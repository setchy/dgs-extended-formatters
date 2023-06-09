package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.language.IntValue;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.InputValueWithState;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;


@DgsDirective(name = DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)
public class AbbreviateDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        IntValue width = (IntValue) Optional.ofNullable(field.getAppliedDirective(DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME))
                .map(directive -> directive.getArgument(DirectiveConstants.ABBREVIATE_DIRECTIVE_ARGUMENT_NAME))
                .map(GraphQLAppliedDirectiveArgument::getArgumentValue)
                .map(InputValueWithState::getValue)
                .filter(IntValue.class::isInstance)
                .orElse(null);

        if (Objects.isNull(width)) {
            throwGraphQLException(DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME, DirectiveConstants.ABBREVIATE_DIRECTIVE_ARGUMENT_NAME);
        }

        return StringUtils.abbreviate(value, width.getValue().intValue());
    }
}