package io.github.setchy.dgs.formatters;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.github.setchy.dgs.formatters.strings.TrimDirective;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end test demonstrating that a directive extending {@link AbstractFormatterDirective}
 * (here, {@link TrimDirective}) applied to an {@code INPUT_FIELD_DEFINITION} location transforms
 * the input field's value before it reaches a resolver, without affecting sibling input fields
 * that share the same declared scalar type but are not themselves annotated, and without
 * regressing the directive's existing {@code FIELD_DEFINITION}/{@code ARGUMENT_DEFINITION} behavior.
 */
class InputFieldDirectiveFormattingIntegrationTest {

    private static final String SDL = """
            directive @trim on FIELD_DEFINITION | ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION

            input SampleInput {
                trimmedField: String @trim
                rawField: String
            }

            type Mutation {
                submit(input: SampleInput!): String
                submitArg(arg: String @trim): String
            }

            type Query {
                trimmedOutput: String @trim
            }
            """;

    private static GraphQLSchema buildSchema() {
        TypeDefinitionRegistry registry = new SchemaParser().parse(SDL);

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .directive("trim", new TrimDirective())
                .build();

        return new SchemaGenerator().makeExecutableSchema(registry, runtimeWiring);
    }

    @Test
    @DisplayName("@trim on INPUT_FIELD_DEFINITION trims the annotated field before it reaches the resolver")
    void trimsAnnotatedInputField() {
        GraphQLSchema schema = buildSchema();

        AtomicReference<Map<String, Object>> capturedArg = new AtomicReference<>();
        DataFetcher<String> fetcher = dfe -> {
            capturedArg.set(dfe.getArgument("input"));
            return "ok";
        };

        GraphQLSchema wired = wireDataFetcher(schema, "Mutation", "submit", fetcher);
        GraphQL graphql = GraphQL.newGraphQL(wired).build();

        String query = """
                mutation {
                    submit(input: { trimmedField: "  hello  ", rawField: "  world  " })
                }""";

        ExecutionResult result = graphql.execute(ExecutionInput.newExecutionInput().query(query).build());
        assertTrue(result.getErrors().isEmpty(), "Expected no execution errors: " + result.getErrors());

        Map<String, Object> input = capturedArg.get();
        assertEquals("hello", input.get("trimmedField"));
    }

    @Test
    @DisplayName("Sibling input field sharing the same scalar type but without @trim is unaffected")
    void doesNotAffectUnannotatedSiblingField() {
        GraphQLSchema schema = buildSchema();

        AtomicReference<Map<String, Object>> capturedArg = new AtomicReference<>();
        DataFetcher<String> fetcher = dfe -> {
            capturedArg.set(dfe.getArgument("input"));
            return "ok";
        };

        GraphQLSchema wired = wireDataFetcher(schema, "Mutation", "submit", fetcher);
        GraphQL graphql = GraphQL.newGraphQL(wired).build();

        String query = """
                mutation {
                    submit(input: { trimmedField: "  hello  ", rawField: "  world  " })
                }""";

        ExecutionResult result = graphql.execute(ExecutionInput.newExecutionInput().query(query).build());
        assertTrue(result.getErrors().isEmpty(), "Expected no execution errors: " + result.getErrors());

        Map<String, Object> input = capturedArg.get();
        assertEquals("  world  ", input.get("rawField"), "Untagged sibling field must pass through unchanged");
    }

    @Test
    @DisplayName("Existing @trim FIELD_DEFINITION behavior is unaffected by INPUT_FIELD_DEFINITION support")
    void existingOutputFieldBehaviorUnaffected() {
        GraphQLSchema schema = buildSchema();
        GraphQL graphql = GraphQL.newGraphQL(schema).build();

        // Directive wrapping happens over the *default* PropertyDataFetcher at schema-build time,
        // so - matching DirectivesIntegrationTest's established pattern - values are supplied via
        // the execution root rather than a post-hoc codeRegistry override (which would bypass the
        // already-wrapped fetcher entirely).
        Map<String, Object> root = Map.of("trimmedOutput", "  padded output  ");

        ExecutionResult result = graphql.execute(ExecutionInput.newExecutionInput()
                .root(root)
                .query("query { trimmedOutput }")
                .build());
        assertTrue(result.getErrors().isEmpty(), "Expected no execution errors: " + result.getErrors());

        Map<String, Object> data = result.getData();
        assertEquals("padded output", data.get("trimmedOutput"));
    }

    @Test
    @DisplayName("Existing @trim ARGUMENT_DEFINITION behavior is unaffected by INPUT_FIELD_DEFINITION support")
    void existingArgumentBehaviorUnaffected() {
        GraphQLSchema schema = buildSchema();
        GraphQL graphql = GraphQL.newGraphQL(schema).build();

        // onArgument wraps the *field's* default data fetcher output (see AbstractFormatterDirective),
        // matching the library's existing (pre-existing, unrelated-to-this-change) semantics.
        Map<String, Object> root = Map.of("submitArg", "  padded arg output  ");

        ExecutionResult result = graphql.execute(ExecutionInput.newExecutionInput()
                .root(root)
                .query("mutation { submitArg(arg: \"whatever\") }")
                .build());
        assertTrue(result.getErrors().isEmpty(), "Expected no execution errors: " + result.getErrors());

        Map<String, Object> data = result.getData();
        assertEquals("padded arg output", data.get("submitArg"));
    }

    private static GraphQLSchema wireDataFetcher(GraphQLSchema schema, String type, String field, DataFetcher<?> fetcher) {
        return schema.transform(builder -> builder.codeRegistry(
                schema.getCodeRegistry().transform(crBuilder -> crBuilder.dataFetcher(
                        FieldCoordinates.coordinates(type, field), fetcher))));
    }
}
