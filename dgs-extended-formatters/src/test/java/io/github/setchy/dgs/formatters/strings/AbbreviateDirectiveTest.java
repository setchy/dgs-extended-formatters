package io.github.setchy.dgs.formatters.strings;

import graphql.GraphQLException;
import graphql.language.IntValue;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.InputValueWithState;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import io.github.setchy.dgs.formatters.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbbreviateDirectiveTest {

    AbbreviateDirective abbreviateDirective;

    @Mock
    GraphQLFieldDefinition field;
    @Mock
    InputValueWithState widthArgumentValue;

    @BeforeEach
    void setUp() {
        abbreviateDirective = new AbbreviateDirective();

        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument argument = mock(GraphQLAppliedDirectiveArgument.class);

        when(field.getAppliedDirective(DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(appliedDirective.getArgument(DirectiveConstants.ABBREVIATE_DIRECTIVE_ARGUMENT_NAME)).thenReturn(argument);
        when(argument.getArgumentValue()).thenReturn(widthArgumentValue);
    }

    @Test
    @DisplayName("Will abbreviate string when width argument provided")
    void testFormatWithSmallWidthArgument() {
        IntValue widthIntValue = mock(IntValue.class);
        when(widthArgumentValue.getValue()).thenReturn(widthIntValue);
        when(widthIntValue.getValue()).thenReturn(BigInteger.valueOf(10));

        String result = abbreviateDirective.applyFormatting(field, TestUtils.SOME_STRING);

        assertEquals("  Some ...", result);
    }

    @Test
    @DisplayName("Will maintain string if width is longer")
    void testFormatWithHugeWidthArgument() {
        IntValue widthIntValue = mock(IntValue.class);
        when(widthArgumentValue.getValue()).thenReturn(widthIntValue);
        when(widthIntValue.getValue()).thenReturn(BigInteger.valueOf(100));

        String result = abbreviateDirective.applyFormatting(field, TestUtils.SOME_STRING);

        assertEquals(TestUtils.SOME_STRING, result);
    }

    @Test
    @DisplayName("Will throw exception when width argument is missing")
    void testFormatWithMissingWidthArgument() {
        when(widthArgumentValue.getValue()).thenReturn(null);

        GraphQLException thrown = assertThrows(GraphQLException.class, () ->
                abbreviateDirective.format(field, TestUtils.SOME_STRING)
        );

        assertEquals("'abbreviate' formatter directive missing required argument 'width'", thrown.getMessage());
    }
}
