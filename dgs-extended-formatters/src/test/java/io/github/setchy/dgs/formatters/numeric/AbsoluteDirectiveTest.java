package io.github.setchy.dgs.formatters.numeric;

import graphql.schema.GraphQLFieldDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AbsoluteDirectiveTest {

    AbsoluteDirective absoluteDirective;

    @Mock
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        absoluteDirective = new AbsoluteDirective();
    }

    @Test
    @DisplayName("Absolute should return same value if positive)")
    void testAbsoluteWithPositiveValue() {
        assertEquals(123, absoluteDirective.applyFormatting(field, 123));
        assertEquals(123.4f, absoluteDirective.applyFormatting(field, 123.4f));
    }

    @Test
    @DisplayName("Absolute should return positive value if negative)")
    void testAbsoluteWithNegativeValue() {
        assertEquals(123, absoluteDirective.applyFormatting(field, -123));
        assertEquals(123.4f, absoluteDirective.applyFormatting(field, -123.4f));
    }

    @Test
    @DisplayName("Absolute should return zero if zero)")
    void testAbsoluteWithZeroValue() {
        assertEquals(0, absoluteDirective.applyFormatting(field, 0));
        assertEquals(0f, absoluteDirective.applyFormatting(field, 0f));
    }
}
