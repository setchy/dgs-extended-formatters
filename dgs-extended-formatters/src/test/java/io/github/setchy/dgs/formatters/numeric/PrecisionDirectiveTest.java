package io.github.setchy.dgs.formatters.numeric;

import graphql.GraphQLException;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.InputValueWithState;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import io.github.setchy.dgs.formatters.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PrecisionDirectiveTest {

    PrecisionDirective precisionDirective;
    GraphQLFieldDefinition field;
    InputValueWithState scaleArgumentValue;


    @BeforeEach
    void setUp() {
        precisionDirective = new PrecisionDirective();
        field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);
        GraphQLAppliedDirectiveArgument argument = mock(GraphQLAppliedDirectiveArgument.class);
        scaleArgumentValue = mock(InputValueWithState.class);

        when(field.getAppliedDirective(DirectiveConstants.PRECISION_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(field.getAppliedDirective(DirectiveConstants.ROUND_UP_DIRECTIVE_NAME)).thenReturn(appliedDirective);
        when(appliedDirective.getArgument(DirectiveConstants.PRECISION_SCALE_ARGUMENT_NAME)).thenReturn(argument);
        when(argument.getArgumentValue()).thenReturn(scaleArgumentValue);
    }
    @Test
    @DisplayName("Should throw error")
    void testPrecisionWithoutScaleArgument() {
        when(scaleArgumentValue.getValue()).thenReturn(null);

        String expectedMessage = "Precision scale not provided";

        Exception exception = assertThrows(GraphQLException.class, () -> precisionDirective.format(field, TestUtils.SOME_FLOAT));
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}
