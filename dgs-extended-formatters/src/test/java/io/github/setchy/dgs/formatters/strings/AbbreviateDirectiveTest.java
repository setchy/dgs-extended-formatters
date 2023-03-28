package io.github.setchy.dgs.formatters.strings;

import graphql.GraphQLException;
import graphql.language.IntValue;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.InputValueWithState;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbbreviateDirectiveTest extends StringDirectiveTestBase {

    AbbreviateDirective abbreviateDirective;
    @BeforeEach
    void setUp() {
        abbreviateDirective= new AbbreviateDirective();
    }

    @Test
    @DisplayName("Will abbreviate string")
    void testFormatWithAbbreviation() {
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument argument = mock(GraphQLAppliedDirectiveArgument.class);
        InputValueWithState argumentValue = mock(InputValueWithState.class);
        IntValue intValue = mock(IntValue.class);

        when(field.getAppliedDirective(DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(appliedDirective.getArgument(DirectiveConstants.ABBREVIATE_DIRECTIVE_ARGUMENT_NAME)).thenReturn(argument);
        when(argument.getArgumentValue()).thenReturn(argumentValue);
        when(argumentValue.getValue()).thenReturn(intValue);
        when(intValue.getValue()).thenReturn(BigInteger.valueOf(10));

        String result = abbreviateDirective.format(field, "This is a long value");

        assertEquals("This is...", result);
    }

    @Test
    @DisplayName("Will throw exception when width is missing")
    void testFormatWithMissingWidth() {
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument argument = mock(GraphQLAppliedDirectiveArgument.class);
        InputValueWithState argumentValue = mock(InputValueWithState.class);
        IntValue intValue = null;

        when(field.getAppliedDirective(DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(appliedDirective.getArgument(DirectiveConstants.ABBREVIATE_DIRECTIVE_ARGUMENT_NAME)).thenReturn(argument);
        when(argument.getArgumentValue()).thenReturn(argumentValue);

        GraphQLException thrown = assertThrows(GraphQLException.class, () -> {
            abbreviateDirective.format(field, "This is a long value");
        });

        assertEquals("Abbreviate formatter directive", thrown.getMessage());
    }
}
