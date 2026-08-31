package io.github.setchy.dgs.formatters.numeric;

import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.AbstractFormatterDirective;

/**
 * Base class for directives that format {@link Integer} or {@link Float} values, passing through
 * any other type unchanged. {@link Double} values are also accepted and normalized to {@link Float}
 * before dispatch - graphql-java's built-in {@code Float} scalar coercion (used for
 * {@code INPUT_FIELD_DEFINITION} locations) produces {@link Double}, not {@link Float}.
 */
public abstract class AbstractNumericDirective extends AbstractFormatterDirective {

    @Override
    public Object format(GraphQLFieldDefinition field, Object value) {
        if (value instanceof Integer intValue) {
            return applyFormatting(field, intValue);
        } else if (value instanceof Float floatValue) {
            return applyFormatting(field, floatValue);
        } else if (value instanceof Double doubleValue) {
            return applyFormatting(field, doubleValue.floatValue());
        }
        return value;
    }

    /**
     * Formats a non-null {@link Integer} value.
     *
     * @param field the field the directive was applied to; may be {@code null} for {@code INPUT_FIELD_DEFINITION}
     * @param value the integer value to format
     * @return the formatted integer
     */
    public abstract Integer applyFormatting(GraphQLFieldDefinition field, Integer value);

    /**
     * Formats a non-null {@link Float} value.
     *
     * @param field the field the directive was applied to; may be {@code null} for {@code INPUT_FIELD_DEFINITION}
     * @param value the float value to format
     * @return the formatted float
     */
    public abstract Float applyFormatting(GraphQLFieldDefinition field, Float value);


}
