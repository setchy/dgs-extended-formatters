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
class TrimDirectiveTest {

    TrimDirective trimDirective;

    @Mock
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        trimDirective = new TrimDirective();
    }

    @Test
    @DisplayName("Should trim string")
    void testTrim() {
        assertEquals("Some string That has MIXED case",
                trimDirective.applyFormatting(field, TestUtils.SOME_STRING));
    }
}
