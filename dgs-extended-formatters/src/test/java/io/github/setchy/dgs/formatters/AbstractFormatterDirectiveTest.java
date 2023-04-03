package io.github.setchy.dgs.formatters;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class AbstractFormatterDirectiveTest {
    @Test
    @DisplayName("Test dummy directive calls format")
    void testAbstractDirectiveCallsFormat() {
        GraphQLSchema schema = buildSchema();
        GraphQL graphql = GraphQL.newGraphQL(schema).build();

        Map<String, Object> root = new HashMap<>();
        root.put("hello", "Hello");
        root.put("world", "World");

        String query = "" +
                "query {\n" +
                "    hello \n" +
                "    world \n" +
                "}";

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .root(root)
                .query(query)
                .build();

        ExecutionResult executionResult = graphql.execute(executionInput);
        Map<String, Object> data = executionResult.getData();

        assertEquals("nothing", data.get("hello"));
        assertEquals("World", data.get("world"));
    }

    private static GraphQLSchema buildSchema() {
        String sdlSpec = "directive @nothing on FIELD_DEFINITION\n" +
                "type Query {\n" +
                "    hello : String @nothing \n" +
                "    world : String \n" +
                "}";

        TypeDefinitionRegistry registry = new SchemaParser().parse(sdlSpec);

        // Create a directive that adds a prefix to the field value
        SchemaDirectiveWiring nothingDirective = new AbstractFormatterDirective() {
            @Override
            public Object format(GraphQLFieldDefinition field, Object value) {
                return "nothing";
            }
        };

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .directive("nothing", nothingDirective)
                .build();

        return new SchemaGenerator().makeExecutableSchema(registry, runtimeWiring);
    }
}
