package io.github.setchy.dgs.formatters.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SwapcaseDirectiveTest extends StringDirectiveTestBase {

    SwapcaseDirective swapcaseDirective;
    @BeforeEach
    void setUp() {
        swapcaseDirective = new SwapcaseDirective();
    }

    @Test
    @DisplayName("Should swap the case of characters within string")
    void testSwapcase() {
        assertEquals("  sOME STRING tHAT HAS mixed CASE  ",
                swapcaseDirective.format(field, SOME_STRING));
    }
}
