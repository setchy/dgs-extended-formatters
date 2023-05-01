package io.github.setchy.dgs.formatters.strings;

import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.AbstractFormatterDirective;

public abstract class AbstractStringDirective extends AbstractFormatterDirective {

    @Override
    public Object format(GraphQLFieldDefinition field, Object value) {
        if (value instanceof String stringValue) {
            return applyFormatting(field, stringValue);
        }
        return value;
    }

    public abstract String applyFormatting(GraphQLFieldDefinition field, String value);


}