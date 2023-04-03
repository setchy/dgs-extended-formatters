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
class FloorDirectiveTest {

    FloorDirective floorDirective;

    @Mock
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        floorDirective = new FloorDirective();
    }

    @Test
    @DisplayName("Floor should round down float with decimal value)")
    void testFloorWithDecimalValue() {
        assertEquals(123.0f, floorDirective.applyFormatting(field, 123.4f));
    }

    @Test
    @DisplayName("Floor should do nothing for whole numbers)")
    void testFloorWithWholeNumber() {
        assertEquals(123, floorDirective.applyFormatting(field, 123));
        assertEquals(500f, floorDirective.applyFormatting(field, 500f));
    }

    @Test
    @DisplayName("Floor should round down for float with negative value)")
    void testFloorWithNegativeValue() {
        assertEquals(-124.0f, floorDirective.applyFormatting(field, -123.4f));
    }
}
