package io.github.setchy.dgs.formatters.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LowercaseDirectiveTest extends StringDirectiveTestBase {

    LowercaseDirective lowercaseDirective;
    @BeforeEach
    void setUp() {
        lowercaseDirective = new LowercaseDirective();
    }

    @Test
    @DisplayName("Should lowercase string")
    void testLowercase() {
        assertEquals("  some string that has mixed case  ",
                lowercaseDirective.format(field, SOME_STRING));
    }
}
