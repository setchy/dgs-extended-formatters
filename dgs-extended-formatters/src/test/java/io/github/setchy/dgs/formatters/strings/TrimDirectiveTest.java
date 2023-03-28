package io.github.setchy.dgs.formatters.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrimDirectiveTest extends StringDirectiveTestBase {

    TrimDirective trimDirective;
    @BeforeEach
    void setUp() {
        trimDirective = new TrimDirective();
    }

    @Test
    @DisplayName("Should trim string")
    void testTrim() {
        assertEquals("Some string That has MIXED case",
                trimDirective.format(field, SOME_STRING));
    }
}
