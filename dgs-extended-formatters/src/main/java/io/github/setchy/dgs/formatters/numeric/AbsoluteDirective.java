package io.github.setchy.dgs.formatters.numeric;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;


@DgsDirective(name = DirectiveConstants.ABSOLUTE_DIRECTIVE_NAME)
public class AbsoluteDirective extends AbstractNumericDirective {

    @Override
    public Integer format(GraphQLFieldDefinition field, Integer value) {
        return Math.abs(value);
    }

    @Override
    public Float format(GraphQLFieldDefinition field, Float value) {
        return Float.valueOf(String.valueOf(Math.abs(value.doubleValue())));
    }
}