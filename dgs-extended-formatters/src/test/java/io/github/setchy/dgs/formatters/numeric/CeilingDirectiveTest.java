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

class CeilingDirectiveTest {

    CeilingDirective ceilingDirective;
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        ceilingDirective = new CeilingDirective();
        field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);

        when(field.getAppliedDirective(DirectiveConstants.CEILING_DIRECTIVE_NAME)).thenReturn(appliedDirective);
    }

    @Test
    @DisplayName("Ceiling should round up for float with decimal value)")
    void testCeilingWithDecimalValue() {
        assertEquals(124.0f, ceilingDirective.format(field, 123.4f));
    }

    @Test
    @DisplayName("Ceiling should do nothing for whole numbers)")
    void testCeilingWithWholeNumber() {
        assertEquals(123, ceilingDirective.format(field, 123));
        assertEquals(500f, ceilingDirective.format(field, 500f));
    }

    @Test
    @DisplayName("Ceiling should round up for float with negative value)")
    void testCeilingWithNegativeValue() {
        assertEquals(-123.0f, ceilingDirective.format(field, -123.4f));
    }
}
