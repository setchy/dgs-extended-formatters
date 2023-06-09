package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.GraphQLException;
import graphql.language.IntValue;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;


@DgsDirective(name = DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)
public class AbbreviateDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        IntValue width = Optional.ofNullable(field.getAppliedDirective(DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)).map(directive -> directive.getArgument(DirectiveConstants.ABBREVIATE_DIRECTIVE_ARGUMENT_NAME)).map(directiveArgument -> directiveArgument.getArgumentValue()).map(argumentValue -> argumentValue.getValue()).filter(argValue -> argValue instanceof IntValue).map(argValue -> (IntValue) argValue).orElse(null);

        if (Objects.isNull(width)) {
            throw new GraphQLException(
                    String.format("'%s' formatter directive missing required argument '%s'",
                            DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME, DirectiveConstants.ABBREVIATE_DIRECTIVE_ARGUMENT_NAME
                    )
            );
        }

        return StringUtils.abbreviate(value, width.getValue().intValue());
    }
}