package io.github.setchy.dgs.formatters.numeric;

import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CeilingDirectiveTest {

    CeilingDirective ceilingDirective;

    @Mock
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        ceilingDirective = new CeilingDirective();
    }

    @Test
    @DisplayName("Ceiling should round up for float with decimal value)")
    void testCeilingWithDecimalValue() {
        assertEquals(124.0f, ceilingDirective.applyFormatting(field, 123.4f));
    }

    @Test
    @DisplayName("Ceiling should do nothing for whole numbers)")
    void testCeilingWithWholeNumber() {
        assertEquals(123, ceilingDirective.applyFormatting(field, 123));
        assertEquals(500f, ceilingDirective.applyFormatting(field, 500f));
    }

    @Test
    @DisplayName("Ceiling should round up for float with negative value)")
    void testCeilingWithNegativeValue() {
        assertEquals(-123.0f, ceilingDirective.applyFormatting(field, -123.4f));
    }
}
