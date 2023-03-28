package io.github.setchy.dgs.formatters.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CapitalizeDirectiveTest extends StringDirectiveTestBase {

    CapitalizeDirective capitalizeDirective;
    @BeforeEach
    void setUp() {
        capitalizeDirective = new CapitalizeDirective();
    }

    @Test
    @DisplayName("Should capitalize string")
    void testCapitalize() {
        assertEquals("  Some String That Has MIXED Case  ",
                capitalizeDirective.format(field, SOME_STRING));
    }
}
