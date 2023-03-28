package io.github.setchy.dgs.formatters.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseDirectiveTest extends StringDirectiveTestBase {

    ReverseDirective reverseDirective;
    @BeforeEach
    void setUp() {
        reverseDirective = new ReverseDirective();
    }

    @Test
    @DisplayName("Should reverse string")
    void testReverse() {
        assertEquals("  esac DEXIM sah tahT gnirts emoS  ",
                reverseDirective.format(field, SOME_STRING));
    }
}
