package io.github.setchy.dgs.formatters.numeric;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;


@DgsDirective(name = DirectiveConstants.CEILING_DIRECTIVE_NAME)
public class CeilingDirective extends AbstractNumericDirective {

    @Override
    public Integer applyFormatting(GraphQLFieldDefinition field, Integer value) {
        return value;
    }

    @Override
    public Float applyFormatting(GraphQLFieldDefinition field, Float value) {
        return Float.valueOf(String.valueOf(Math.ceil(value.doubleValue())));
    }
}