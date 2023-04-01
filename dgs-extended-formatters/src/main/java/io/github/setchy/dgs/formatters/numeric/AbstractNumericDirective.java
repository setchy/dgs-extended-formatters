package io.github.setchy.dgs.formatters.numeric;

import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.AbstractFormatterDirective;

public abstract class AbstractNumericDirective extends AbstractFormatterDirective {

    @Override
    public Object format(GraphQLFieldDefinition field, Object value) {
        if (value instanceof Integer intValue) {
            return applyFormatting(field, intValue);
        } else if (value instanceof Float floatValue) {
            return applyFormatting(field, floatValue);
        }
        return value;
    }

    public abstract Integer applyFormatting(GraphQLFieldDefinition field, Integer value);

    public abstract Float applyFormatting(GraphQLFieldDefinition field, Float value);


}