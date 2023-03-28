package io.github.setchy.dgs.formatters.floats;

import graphql.GraphQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrecisionDirectiveTest extends FloatDirectiveTestBase {

    PrecisionDirective precisionDirective;
    @BeforeEach
    void setUp() {
        precisionDirective = new PrecisionDirective();
    }

    @Test
    @DisplayName("Should throw error")
    void testPrecision() {
        Exception exception = assertThrows(GraphQLException.class, () -> precisionDirective.format(field, SOME_FLOAT));

        String expectedMessage = "Precision directive is not yet implemented";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
