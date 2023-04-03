package io.github.setchy.dgs.formatters;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.github.setchy.dgs.formatters.numeric.AbsoluteDirective;
import io.github.setchy.dgs.formatters.strings.UppercaseDirective;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectivesIntegrationTest {
    @Test
    @DisplayName("Test directives against simple schema")
    void testAbstractDirectiveCallsFormat() {
        GraphQLSchema schema = buildSchema();
        GraphQL graphql = GraphQL.newGraphQL(schema).build();

        Map<String, Object> root = new HashMap<>();
        root.put("helloWorld", "Hello World");
        root.put("helloWorldShouting", "Hello World");
        root.put("negativeNumber", -100);
        root.put("absoluteNumber", -100);

        String query = """
                query {
                    helloWorld
                    helloWorldShouting
                    negativeNumber
                    absoluteNumber
                }""";

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .root(root)
                .query(query)
                .build();

        ExecutionResult executionResult = graphql.execute(executionInput);
        Map<String, Object> data = executionResult.getData();

        assertEquals("Hello World", data.get("helloWorld"));
        assertEquals("HELLO WORLD", data.get("helloWorldShouting"));
        assertEquals(-100, data.get("negativeNumber"));
        assertEquals(100, data.get("absoluteNumber"));
    }

    private static GraphQLSchema buildSchema() {
        String sdlSpec = """
                directive @absolute on FIELD_DEFINITION
                directive @uppercase on FIELD_DEFINITION

                type Query {
                  helloWorld : String
                  helloWorldShouting : String @uppercase
                  negativeNumber: Int
                  absoluteNumber: Int @absolute
                }""";

        TypeDefinitionRegistry registry = new SchemaParser().parse(sdlSpec);

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .directive("uppercase", new UppercaseDirective())
                .directive("absolute", new AbsoluteDirective())
                .build();

        return new SchemaGenerator().makeExecutableSchema(registry, runtimeWiring);
    }
}
