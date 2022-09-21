package io.github.setchy.dgs.formatters.floats;

import com.netflix.graphql.dgs.DgsDirective;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import graphql.GraphQLException;
import graphql.schema.GraphQLFieldDefinition;


@DgsDirective(name = DirectiveConstants.PRECISION_DIRECTIVE_NAME)
public class PrecisionDirective extends AbstractFloatDirective {

    @Override
    public Float format(GraphQLFieldDefinition field, Float value) {
        throw new GraphQLException("Precision directive not implemented yet");
    }
}