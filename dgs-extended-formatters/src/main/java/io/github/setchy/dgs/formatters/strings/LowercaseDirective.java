package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;


@DgsDirective(name = DirectiveConstants.LOWERCASE_DIRECTIVE_NAME)
public class LowercaseDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        return value.toLowerCase();
    }
}