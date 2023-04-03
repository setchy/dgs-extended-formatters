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
class ReverseDirectiveTest {

    ReverseDirective reverseDirective;

    @Mock
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        reverseDirective = new ReverseDirective();
    }

    @Test
    @DisplayName("Should reverse string")
    void testReverse() {
        assertEquals("  esac DEXIM sah tahT gnirts emoS  ",
                reverseDirective.applyFormatting(field, TestUtils.SOME_STRING));
    }
}
