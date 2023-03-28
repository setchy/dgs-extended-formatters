package io.github.setchy.dgs.formatters.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CamelcaseDirectiveTest extends StringDirectiveTestBase {

    CamelcaseDirective camelcaseDirective;
    @BeforeEach
    void setUp() {
        camelcaseDirective = new CamelcaseDirective();
    }

    @Test
    @DisplayName("Should camelcase string")
    void testCamelcase() {
        assertEquals("someStringThatHasMixedCase",
                camelcaseDirective.format(field, SOME_STRING));
    }
}
