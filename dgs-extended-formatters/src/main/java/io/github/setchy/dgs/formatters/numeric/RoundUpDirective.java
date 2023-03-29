package io.github.setchy.dgs.formatters.numeric;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.GraphQLException;
import graphql.language.IntValue;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;


@DgsDirective(name = DirectiveConstants.ROUND_UP_DIRECTIVE_NAME)
public class RoundUpDirective extends AbstractNumericDirective {

    @Override
    public Integer format(GraphQLFieldDefinition field, Integer value) {
        return value;
    }

    @Override
    public Float format(GraphQLFieldDefinition field, Float value) {
        IntValue scale = (IntValue) field.getAppliedDirective(DirectiveConstants.ROUND_UP_DIRECTIVE_NAME)
                .getArgument(DirectiveConstants.PRECISION_SCALE_ARGUMENT_NAME)
                .getArgumentValue()
                .getValue();

        if (scale == null) {
            throw new GraphQLException("Precision scale argument not provided");
        }

        int n = scale.getValue().intValue();

        String formattedString = String.format("%." + n + "f", value);
        return Float.parseFloat(formattedString);
    }
}