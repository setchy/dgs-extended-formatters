package io.github.setchy.dgs.formatters.strings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import graphql.schema.GraphQLFieldDefinition;
import org.junit.jupiter.api.Test;

class AbstractStringDirectiveTest {

    private AbstractStringDirective directive = new AbstractStringDirective() {
        @Override
        public String applyFormatting(GraphQLFieldDefinition field, String value) {
            return value.toUpperCase();
        }
    };

    @Test
    void format_withString_returnsFormattedValue() {
        // given
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        String value = "hello";

        // when
        Object result = directive.format(field, value);

        // then
        assertEquals("HELLO", result);
    }

    @Test
    void format_withNonStringValue_returnsOriginalValue() {
        // given
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        Integer value = 10;

        // when
        Object result = directive.format(field, value);

        // then
        assertEquals(value, result);
    }
}
