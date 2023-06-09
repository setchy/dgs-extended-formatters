package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.GraphQLException;
import graphql.language.StringValue;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;

import java.util.Objects;
import java.util.Optional;


@DgsDirective(name = DirectiveConstants.PREFIX_DIRECTIVE_NAME)
public class PrefixDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        StringValue withArg = Optional.ofNullable(field.getAppliedDirective(DirectiveConstants.PREFIX_DIRECTIVE_NAME))
                .map(directive -> directive.getArgument(DirectiveConstants.PREFIX_DIRECTIVE_ARGUMENT_NAME))
                .map(directiveArgument -> directiveArgument.getArgumentValue())
                .map(argumentValue -> argumentValue.getValue())
                .filter(argValue -> argValue instanceof StringValue)
                .map(argValue -> (StringValue) argValue)
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