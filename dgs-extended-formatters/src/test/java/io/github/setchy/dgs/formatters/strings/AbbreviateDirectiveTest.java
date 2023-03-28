package io.github.setchy.dgs.formatters.strings;

import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static graphql.Scalars.GraphQLString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AbbreviateDirectiveTest extends StringDirectiveTestBase {

    AbbreviateDirective abbreviateDirective;
    @BeforeEach
    void setUp() {
        abbreviateDirective= new AbbreviateDirective();
    }

    @Test
    @Disabled
    @DisplayName("Should abbreviate string with zero width")
    void testAbbreviateWithZeroWidth() {
        GraphQLArgument arg = GraphQLArgument
                .newArgument()
                .name(DirectiveConstants.ABBREVIATE_DIRECTIVE_ARGUMENT_NAME)
                .type(GraphQLString)
                .build();

        GraphQLFieldDefinition fieldz = GraphQLFieldDefinition
                .newFieldDefinition()
                .name("foo")
                .type(GraphQLString)
                .argument(arg)
                .build();
        assertEquals("someStringThatHasMixedCase",
                abbreviateDirective.format(fieldz, SOME_STRING));
    }
}
