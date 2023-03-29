package io.github.setchy.dgs.formatters.numeric;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.GraphQLException;
import graphql.language.IntValue;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;

import java.math.BigDecimal;


@DgsDirective(name = DirectiveConstants.PRECISION_DIRECTIVE_NAME)
public class PrecisionDirective extends AbstractNumericDirective {

    @Override
    public Integer format(GraphQLFieldDefinition field, Integer value) {
        return value;
    }

    @Override
    public Float format(GraphQLFieldDefinition field, Float value) {
        IntValue scale = (IntValue) field.getAppliedDirective(DirectiveConstants.PRECISION_DIRECTIVE_NAME)
                .getArgument(DirectiveConstants.PRECISION_SCALE_ARGUMENT_NAME)
                .getArgumentValue()
                .getValue();

        if (scale == null) {
            throw new GraphQLException("Precision scale not provided");
        }

        BigDecimal roundedValue = new BigDecimal(value).setScale(scale.getValue().intValue(),
                BigDecimal.ROUND_HALF_UP);

        return roundedValue.floatValue();
    }
}