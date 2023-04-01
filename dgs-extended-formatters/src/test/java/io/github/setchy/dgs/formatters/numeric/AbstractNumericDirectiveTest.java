package io.github.setchy.dgs.formatters.numeric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import graphql.schema.GraphQLFieldDefinition;
import org.junit.jupiter.api.Test;

class AbstractNumericDirectiveTest {

    private AbstractNumericDirective directive = new AbstractNumericDirective() {
        @Override
        public Integer applyFormatting(GraphQLFieldDefinition field, Integer value) {
            return value + 1;
        }

        @Override
        public Float applyFormatting(GraphQLFieldDefinition field, Float value) {
            return value + 1.0f;
        }
    };

    @Test
    void applyFormatting_withInteger_returnsFormattedValue() {
        // given
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        Integer value = 10;

        // when
        Object result = directive.format(field, value);

        // then
        assertEquals(11, result);
    }

    @Test
    void applyFormatting_withFloat_returnsFormattedValue() {
        // given
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        Float value = 10.0f;

        // when
        Object result = directive.format(field, value);

        // then
        assertEquals(11.0f, result);
    }

    @Test
    void applyFormatting_withNonNumericValue_returnsOriginalValue() {
        // given
        GraphQLFieldDefinition field = mock(GraphQLFieldDefinition.class);
        String value = "abc";

        // when
        Object result = directive.format(field, value);

        // then
        assertEquals(value, result);
    }
}
