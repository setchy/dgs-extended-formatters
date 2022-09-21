package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import graphql.GraphQLException;
import graphql.language.IntValue;
import graphql.schema.GraphQLFieldDefinition;
import org.apache.commons.lang3.StringUtils;


@DgsDirective(name = DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)
public class AbbreviateDirective extends AbstractStringDirective {

    @Override
    public String format(GraphQLFieldDefinition field, String value) {
        IntValue width = (IntValue) field.getAppliedDirective(DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)
            .getArgument("width")
            .getArgumentValue()
            .getValue();

        if (width == null) {
            throw new GraphQLException("Abbreviate formatter directive");
        }

        return StringUtils.abbreviate(value, width.getValue()
            .intValue());
    }
}