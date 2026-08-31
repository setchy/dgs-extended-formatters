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
import io.github.setchy.dgs.formatters.numeric.AbsoluteDirective;
import io.github.setchy.dgs.formatters.numeric.CeilingDirective;
import io.github.setchy.dgs.formatters.numeric.FloorDirective;
import io.github.setchy.dgs.formatters.strings.CamelcaseDirective;
import io.github.setchy.dgs.formatters.strings.CapitalizeDirective;
import io.github.setchy.dgs.formatters.strings.LowercaseDirective;
import io.github.setchy.dgs.formatters.strings.ReverseDirective;
import io.github.setchy.dgs.formatters.strings.SwapcaseDirective;
import io.github.setchy.dgs.formatters.strings.UppercaseDirective;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end tests demonstrating that the nine no-argument string/numeric directives
 * (which never dereference the {@code field} parameter passed to {@code format}/{@code applyFormatting})
 * correctly transform {@code INPUT_FIELD_DEFINITION} values via the same {@code onInputObjectField}
 * mechanism validated for {@code @trim}, without affecting untagged sibling fields.
 * <p>
 * Numeric directives are only exercised against {@code Int} input fields. Applying them to a
 * {@code Float} input field does not currently work: graphql-java's built-in {@code Float} scalar
 * coercion produces {@code java.lang.Double}, which {@code AbstractNumericDirective.format} does not
 * pattern-match (see design.md for this change) - this is a documented, pre-existing limitation, not
 * something this change fixes.
 */
class NoArgumentInputFieldDirectivesIntegrationTest {

    private static final String SDL = """
            directive @uppercase on INPUT_FIELD_DEFINITION
            directive @lowercase on INPUT_FIELD_DEFINITION
            directive @capitalize on INPUT_FIELD_DEFINITION
            directive @reverse on INPUT_FIELD_DEFINITION
            directive @swapcase on INPUT_FIELD_DEFINITION
            directive @camelcase on INPUT_FIELD_DEFINITION
            directive @absolute on INPUT_FIELD_DEFINITION
            directive @ceiling on INPUT_FIELD_DEFINITION
            directive @floor on INPUT_FIELD_DEFINITION

            input SampleInput {
                uppercased: String @uppercase
                lowercased: String @lowercase
                capitalized: String @capitalize
                reversed: String @reverse
                swapcased: String @swapcase
                camelcased: String @camelcase
                absoluteInt: Int @absolute
                ceilingInt: Int @ceiling
                flooredInt: Int @floor
                rawField: String
            }

            type Mutation {
                submit(input: SampleInput!): String
            }

            type Query {
                noop: String
            }
            """;

    private static GraphQLSchema buildSchema() {
        TypeDefinitionRegistry registry = new SchemaParser().parse(SDL);

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .directive("uppercase", new UppercaseDirective())
                .directive("lowercase", new LowercaseDirective())
                .directive("capitalize", new CapitalizeDirective())
                .directive("reverse", new ReverseDirective())
                .directive("swapcase", new SwapcaseDirective())
                .directive("camelcase", new CamelcaseDirective())
                .directive("absolute", new AbsoluteDirective())
                .directive("ceiling", new CeilingDirective())
                .directive("floor", new FloorDirective())
                .build();

        return new SchemaGenerator().makeExecutableSchema(registry, runtimeWiring);
    }

    private static Map<String, Object> submit(String inputLiteral) {
        GraphQLSchema schema = buildSchema();

        AtomicReference<Map<String, Object>> capturedArg = new AtomicReference<>();
        DataFetcher<String> fetcher = dfe -> {
            capturedArg.set(dfe.getArgument("input"));
            return "ok";
        };

        GraphQLSchema wired = schema.transform(builder -> builder.codeRegistry(
                schema.getCodeRegistry().transform(crBuilder -> crBuilder.dataFetcher(
                        FieldCoordinates.coordinates("Mutation", "submit"), fetcher))));

        GraphQL graphql = GraphQL.newGraphQL(wired).build();

        ExecutionResult result = graphql.execute(ExecutionInput.newExecutionInput()
                .query("mutation { submit(input: " + inputLiteral + ") }")
                .build());
        assertTrue(result.getErrors().isEmpty(), "Expected no execution errors: " + result.getErrors());

        return capturedArg.get();
    }

    @Test
    @DisplayName("@uppercase on INPUT_FIELD_DEFINITION uppercases the annotated field")
    void uppercaseTransformsInputField() {
        Map<String, Object> input = submit("{ uppercased: \"hello\" }");
        assertEquals("HELLO", input.get("uppercased"));
    }

    @Test
    @DisplayName("@lowercase on INPUT_FIELD_DEFINITION lowercases the annotated field")
    void lowercaseTransformsInputField() {
        Map<String, Object> input = submit("{ lowercased: \"HELLO\" }");
        assertEquals("hello", input.get("lowercased"));
    }

    @Test
    @DisplayName("@capitalize on INPUT_FIELD_DEFINITION capitalizes the annotated field")
    void capitalizeTransformsInputField() {
        Map<String, Object> input = submit("{ capitalized: \"hello world\" }");
        assertEquals("Hello World", input.get("capitalized"));
    }

    @Test
    @DisplayName("@reverse on INPUT_FIELD_DEFINITION reverses the annotated field")
    void reverseTransformsInputField() {
        Map<String, Object> input = submit("{ reversed: \"hello\" }");
        assertEquals("olleh", input.get("reversed"));
    }

    @Test
    @DisplayName("@swapcase on INPUT_FIELD_DEFINITION swaps the case of the annotated field")
    void swapcaseTransformsInputField() {
        Map<String, Object> input = submit("{ swapcased: \"Hello\" }");
        assertEquals("hELLO", input.get("swapcased"));
    }

    @Test
    @DisplayName("@camelcase on INPUT_FIELD_DEFINITION camel-cases the annotated field")
    void camelcaseTransformsInputField() {
        Map<String, Object> input = submit("{ camelcased: \"hello world\" }");
        assertEquals("helloWorld", input.get("camelcased"));
    }

    @Test
    @DisplayName("@absolute on INPUT_FIELD_DEFINITION takes the absolute value of the annotated Int field")
    void absoluteTransformsIntInputField() {
        Map<String, Object> input = submit("{ absoluteInt: -5 }");
        assertEquals(5, input.get("absoluteInt"));
    }

    @Test
    @DisplayName("@ceiling on INPUT_FIELD_DEFINITION leaves an Int field unchanged (identity, matching FIELD_DEFINITION semantics)")
    void ceilingIsIdentityOnIntInputField() {
        Map<String, Object> input = submit("{ ceilingInt: 3 }");
        assertEquals(3, input.get("ceilingInt"));
    }

    @Test
    @DisplayName("@floor on INPUT_FIELD_DEFINITION leaves an Int field unchanged (identity, matching FIELD_DEFINITION semantics)")
    void floorIsIdentityOnIntInputField() {
        Map<String, Object> input = submit("{ flooredInt: 3 }");
        assertEquals(3, input.get("flooredInt"));
    }

    @Test
    @DisplayName("Untagged sibling field sharing the String scalar type is unaffected")
    void untaggedSiblingFieldUnaffected() {
        Map<String, Object> input = submit("{ uppercased: \"hello\", rawField: \"  untouched  \" }");
        assertEquals("  untouched  ", input.get("rawField"));
    }
}
