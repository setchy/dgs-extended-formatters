package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.lang3.StringUtils;


@DgsDirective(name = DirectiveConstants.REVERSE_DIRECTIVE_NAME)
public class ReverseDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        return StringUtils.reverse(value);
    }
}