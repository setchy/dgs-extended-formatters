package io.github.setchy.dgs.formatters.strings;

import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import io.github.setchy.dgs.formatters.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LowercaseDirectiveTest {

    LowercaseDirective lowercaseDirective;

    @Mock
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        lowercaseDirective = new LowercaseDirective();
    }

    @Test
    @DisplayName("Should lowercase string")
    void testLowercase() {
        assertEquals("  some string that has mixed case  ",
                lowercaseDirective.applyFormatting(field, TestUtils.SOME_STRING));
    }
}
