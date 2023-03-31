package io.github.setchy.dgs.formatters.numeric;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;


@DgsDirective(name = DirectiveConstants.FLOOR_DIRECTIVE_NAME)
public class FloorDirective extends AbstractNumericDirective {

    @Override
    public Integer format(GraphQLFieldDefinition field, Integer value) {
        return value;
    }

    @Override
    public Float format(GraphQLFieldDefinition field, Float value) {
        return Float.valueOf(String.valueOf(Math.floor(value.doubleValue())));
    }
}