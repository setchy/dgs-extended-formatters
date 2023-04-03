package io.github.setchy.dgs.formatters.strings;

import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SwapcaseDirectiveTest {

    SwapcaseDirective swapcaseDirective;

    @Mock
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        swapcaseDirective = new SwapcaseDirective();
    }

    @Test
    @DisplayName("Should swap the case of characters within string")
    void testSwapcase() {
        assertEquals("  sOME STRING tHAT HAS mixed CASE  ",
                swapcaseDirective.applyFormatting(field, TestUtils.SOME_STRING));
    }
}
