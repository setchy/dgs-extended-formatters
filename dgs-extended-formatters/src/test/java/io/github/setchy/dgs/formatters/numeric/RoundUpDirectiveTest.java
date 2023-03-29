package io.github.setchy.dgs.formatters.numeric;

import graphql.GraphQLException;
import graphql.language.IntValue;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.InputValueWithState;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import io.github.setchy.dgs.formatters.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoundUpDirectiveTest {

    RoundUpDirective roundUpDirective;
    GraphQLFieldDefinition field;
    InputValueWithState scaleArgumentValue;


    @BeforeEach
    void setUp() {
        roundUpDirective = new RoundUpDirective();
        field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument argument = mock(GraphQLAppliedDirectiveArgument.class);
        scaleArgumentValue = mock(InputValueWithState.class);

        when(field.getAppliedDirective(DirectiveConstants.ROUND_UP_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(appliedDirective.getArgument(DirectiveConstants.PRECISION_SCALE_ARGUMENT_NAME)).thenReturn(argument);
        when(argument.getArgumentValue()).thenReturn(scaleArgumentValue);
    }

    @Test
    @DisplayName("Should throw error when no scale argument provided")
    void testRoundUpWithoutScaleArgument() {
        when(scaleArgumentValue.getValue()).thenReturn(null);

        String expectedMessage = "Precision scale argument not provided";

        Exception exception = assertThrows(GraphQLException.class, () -> roundUpDirective.format(field, TestUtils.SOME_FLOAT));
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should scale to 0")
    void testRoundUpWithScaleArgumentOfZero() {
        int scale = 0;

        IntValue scaleIntValue = mock(IntValue.class);
        when(scaleArgumentValue.getValue()).thenReturn(scaleIntValue);
        when(scaleIntValue.getValue()).thenReturn(BigInteger.valueOf(scale));

        float result = roundUpDirective.format(field, TestUtils.SOME_FLOAT);
        String resultAsString = String.format("%." + scale + "f", result);

        assertEquals("12345", resultAsString);
    }

    @Test
    @DisplayName("Should round up when scale argument provided")
    void testRoundUpWithScaleArgument() {
        int scale = 2;

        IntValue scaleIntValue = mock(IntValue.class);
        when(scaleArgumentValue.getValue()).thenReturn(scaleIntValue);
        when(scaleIntValue.getValue()).thenReturn(BigInteger.valueOf(scale));

        float result = roundUpDirective.format(field, TestUtils.SOME_FLOAT);
        String resultAsString = String.format("%." + scale + "f", result);

        assertEquals("12345.49", resultAsString);
    }

    @Test
    @Disabled("Quirky rounding going on")
    @DisplayName("Should get back the same value if scale is too large (> 7)")
    void testRoundUpWithLargeScaleArgument() {
        int scale = 8;

        IntValue scaleIntValue = mock(IntValue.class);
        when(scaleArgumentValue.getValue()).thenReturn(scaleIntValue);
        when(scaleIntValue.getValue()).thenReturn(BigInteger.valueOf(scale));

        float result = roundUpDirective.format(field, TestUtils.SOME_FLOAT);
        String resultAsString = String.format("%." + scale + "f", result);

        assertEquals("12345.49316406", resultAsString);
    }

}
