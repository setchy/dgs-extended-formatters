package io.github.setchy.dgs.formatters.numeric;

import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FloorDirectiveTest {

    FloorDirective floorDirective;
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        floorDirective = new FloorDirective();
        field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);

        when(field.getAppliedDirective(DirectiveConstants.FLOOR_DIRECTIVE_NAME)).thenReturn(appliedDirective);
    }

    @Test
    @DisplayName("Floor should round down float with decimal value)")
    void testFloorWithDecimalValue() {
        assertEquals(123.0f, floorDirective.format(field, 123.4f));
    }

    @Test
    @DisplayName("Floor should do nothing for whole numbers)")
    void testFloorWithWholeNumber() {
        assertEquals(123, floorDirective.format(field, 123));
        assertEquals(500f, floorDirective.format(field, 500f));
    }

    @Test
    @DisplayName("Floor should round down for float with negative value)")
    void testFloorWithNegativeValue() {
        assertEquals(-124.0f, floorDirective.format(field, -123.4f));
    }
}
