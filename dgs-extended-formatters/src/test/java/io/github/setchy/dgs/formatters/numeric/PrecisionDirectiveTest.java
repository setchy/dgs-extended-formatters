package io.github.setchy.dgs.formatters.numeric;

import graphql.GraphQLException;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;
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

    @BeforeEach
    void setUp() {
        precisionDirective = new PrecisionDirective();
        field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);

        when(field.getAppliedDirective(DirectiveConstants.PRECISION_DIRECTIVE_NAME)).thenReturn(appliedDirective);
    }
    @Test
    @DisplayName("Should throw error")
    void testPrecision() {
        String expectedMessage = "Precision directive is not yet implemented";

        Exception exception = assertThrows(GraphQLException.class, () -> precisionDirective.format(field, TestUtils.SOME_FLOAT));
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}
