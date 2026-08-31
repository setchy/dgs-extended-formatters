package io.github.setchy.dgs.formatters.numeric;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;


/** Returns the floor value of a Float; Integer values are returned unchanged. */
@DgsDirective(name = DirectiveConstants.FLOOR_DIRECTIVE_NAME)
public class FloorDirective extends AbstractNumericDirective {

    @Override
    public Integer applyFormatting(GraphQLFieldDefinition field, Integer value) {
        return value;
    }

    @Override
    public Float applyFormatting(GraphQLFieldDefinition field, Float value) {
        return Float.valueOf(String.valueOf(Math.floor(value.doubleValue())));
    }
}