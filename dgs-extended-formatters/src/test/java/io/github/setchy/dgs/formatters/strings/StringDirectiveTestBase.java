package io.github.setchy.dgs.formatters.strings;

import graphql.schema.GraphQLFieldDefinition;
import org.junit.jupiter.api.BeforeEach;

import static graphql.Scalars.GraphQLString;

class StringDirectiveTestBase {

    static String SOME_STRING = "  Some string That has MIXED case  ";
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        field = GraphQLFieldDefinition
                .newFieldDefinition()
                .name("foo")
                .type(GraphQLString)
                .build();
    }

}
