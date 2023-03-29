package io.github.setchy.dgs.formatters.strings;

import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import io.github.setchy.dgs.formatters.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UppercaseDirectiveTest {

    UppercaseDirective uppercaseDirective;
    GraphQLFieldDefinition field;

    @BeforeEach
    void setUp() {
        uppercaseDirective = new UppercaseDirective();
        field = mock(GraphQLFieldDefinition.class);
        GraphQLAppliedDirective appliedDirective = mock(GraphQLAppliedDirective.class);

        when(field.getAppliedDirective(DirectiveConstants.UPPERCASE_DIRECTIVE_NAME)).thenReturn(appliedDirective);
    }

    @Test
    @DisplayName("Should uppercase string")
    void testUppercase() {
        assertEquals("  SOME STRING THAT HAS MIXED CASE  ",
                uppercaseDirective.format(field, TestUtils.SOME_STRING));
    }
}
