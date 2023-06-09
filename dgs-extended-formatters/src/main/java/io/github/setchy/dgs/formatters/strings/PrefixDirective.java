package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.GraphQLException;
import graphql.language.StringValue;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.InputValueWithState;
import io.github.setchy.dgs.formatters.DirectiveConstants;

import java.util.Objects;
import java.util.Optional;


@DgsDirective(name = DirectiveConstants.PREFIX_DIRECTIVE_NAME)
public class PrefixDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        StringValue withArg = (StringValue) Optional.ofNullable(field.getAppliedDirective(DirectiveConstants.PREFIX_DIRECTIVE_NAME))
                .map(directive -> directive.getArgument(DirectiveConstants.PREFIX_DIRECTIVE_ARGUMENT_NAME))
                .map(GraphQLAppliedDirectiveArgument::getArgumentValue)
                .map(InputValueWithState::getValue)
                .filter(StringValue.class::isInstance)
                .orElse(null);

        if (Objects.isNull(withArg)) {
            throw new GraphQLException(
                    String.format("'%s' formatter directive missing required argument '%s'",
                            DirectiveConstants.PREFIX_DIRECTIVE_NAME, DirectiveConstants.PREFIX_DIRECTIVE_ARGUMENT_NAME
                    )
            );
        }

        return withArg.getValue().concat(value);
    }
}