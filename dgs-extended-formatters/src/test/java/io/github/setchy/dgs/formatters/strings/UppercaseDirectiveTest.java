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
class UppercaseDirectiveTest {

    UppercaseDirective uppercaseDirective;

    @Mock
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        uppercaseDirective = new UppercaseDirective();
    }

    @Test
    @DisplayName("Should uppercase string")
    void testUppercase() {
        assertEquals("  SOME STRING THAT HAS MIXED CASE  ",
                uppercaseDirective.applyFormatting(field, TestUtils.SOME_STRING));
    }
}
