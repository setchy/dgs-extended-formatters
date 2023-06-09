package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.GraphQLException;
import graphql.language.StringValue;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;

import java.util.Objects;


@DgsDirective(name = DirectiveConstants.PREFIX_DIRECTIVE_NAME)
public class PrefixDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        StringValue withArg = (StringValue) field.getAppliedDirective(DirectiveConstants.PREFIX_DIRECTIVE_NAME)
                .getArgument(DirectiveConstants.PREFIX_DIRECTIVE_ARGUMENT_NAME)
                .getArgumentValue()
                .getValue();

        if (Objects.isNull(withArg)) {
            throw new GraphQLException("Prefix formatter directive missing required argument");
        }

        return withArg.getValue().concat(value);
    }
}