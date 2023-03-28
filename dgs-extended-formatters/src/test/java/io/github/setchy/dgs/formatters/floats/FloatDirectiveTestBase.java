package io.github.setchy.dgs.formatters.floats;

import graphql.schema.GraphQLFieldDefinition;
import org.junit.jupiter.api.BeforeEach;

import static graphql.Scalars.GraphQLFloat;

class FloatDirectiveTestBase {

    static Float SOME_FLOAT = 1.234F;
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        field = GraphQLFieldDefinition
                .newFieldDefinition()
                .name("foo")
                .type(GraphQLFloat)
                .build();
    }

}
