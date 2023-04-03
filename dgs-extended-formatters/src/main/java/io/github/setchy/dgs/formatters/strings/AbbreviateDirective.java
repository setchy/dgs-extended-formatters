package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.GraphQLException;
import graphql.language.IntValue;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.lang3.StringUtils;


@DgsDirective(name = DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)
public class AbbreviateDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        IntValue width = (IntValue) field.getAppliedDirective(DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)
                .getArgument(DirectiveConstants.ABBREVIATE_DIRECTIVE_ARGUMENT_NAME)
                .getArgumentValue()
                .getValue();

        if (width == null) {
            throw new GraphQLException("Abbreviate formatter directive missing required argument");
        }

        return StringUtils.abbreviate(value, width.getValue().intValue());
    }
}