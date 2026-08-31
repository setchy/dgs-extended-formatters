package io.github.setchy.dgs.formatters.strings;

import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.AbstractFormatterDirective;

/** Base class for directives that format {@link String} values, passing through any other type unchanged. */
public abstract class AbstractStringDirective extends AbstractFormatterDirective {

    @Override
    public Object format(GraphQLFieldDefinition field, Object value) {
        if (value instanceof String stringValue) {
            return applyFormatting(field, stringValue);
        }
        return value;
    }

    /**
     * Formats a non-null {@link String} value.
     *
     * @param field the field the directive was applied to; may be {@code null} for {@code INPUT_FIELD_DEFINITION}
     * @param value the string value to format
     * @return the formatted string
     */
    public abstract String applyFormatting(GraphQLFieldDefinition field, String value);


}