package io.github.setchy.dgs.formatters.numeric;

import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbsoluteDirectiveTest {

    AbsoluteDirective absoluteDirective;
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        absoluteDirective = new AbsoluteDirective();
        field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);

        when(field.getAppliedDirective(DirectiveConstants.ABSOLUTE_DIRECTIVE_NAME)).thenReturn(appliedDirective);
    }

    @Test
    @DisplayName("Absolute should return same value if positive)")
    void testAbsoluteWithPositiveValue() {
        assertEquals(123, absoluteDirective.format(field, 123));
        assertEquals(123.4f, absoluteDirective.format(field, 123.4f));
    }

    @Test
    @DisplayName("Absolute should return positive value if negative)")
    void testAbsoluteWithNegativeValue() {
        assertEquals(123, absoluteDirective.format(field, -123));
        assertEquals(123.4f, absoluteDirective.format(field, -123.4f));
    }

    @Test
    @DisplayName("Absolute should return zero if zero)")
    void testAbsoluteWithZeroValue() {
        assertEquals(0, absoluteDirective.format(field, 0));
        assertEquals(0f, absoluteDirective.format(field, 0f));
    }
}
