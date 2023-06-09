package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.language.StringValue;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.InputValueWithState;
import io.github.setchy.dgs.formatters.DirectiveConstants;

import java.util.Objects;
import java.util.Optional;


@DgsDirective(name = DirectiveConstants.SUFFIX_DIRECTIVE_NAME)
public class SuffixDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        StringValue withArg = (StringValue) Optional.ofNullable(field.getAppliedDirective(DirectiveConstants.SUFFIX_DIRECTIVE_NAME))
                .map(directive -> directive.getArgument(DirectiveConstants.SUFFIX_DIRECTIVE_ARGUMENT_NAME))
                .map(GraphQLAppliedDirectiveArgument::getArgumentValue)
                .map(InputValueWithState::getValue)
                .filter(StringValue.class::isInstance)
                .orElse(null);

        if (Objects.isNull(withArg)) {
            throwGraphQLException(DirectiveConstants.SUFFIX_DIRECTIVE_NAME, DirectiveConstants.SUFFIX_DIRECTIVE_ARGUMENT_NAME);
        }

        return value.concat(withArg.getValue());
    }
}