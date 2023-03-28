package io.github.setchy.dgs.formatters.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UppercaseDirectiveTest extends StringDirectiveTestBase {

    UppercaseDirective uppercaseDirective;
    @BeforeEach
    void setUp() {
        uppercaseDirective = new UppercaseDirective();
    }

    @Test
    @DisplayName("Should uppercase string")
    void testUppercase() {
        assertEquals("  SOME STRING THAT HAS MIXED CASE  ",
                uppercaseDirective.format(field, SOME_STRING));
    }
}
