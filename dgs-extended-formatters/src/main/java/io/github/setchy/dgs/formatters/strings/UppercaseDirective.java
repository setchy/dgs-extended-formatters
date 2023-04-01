package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;


@DgsDirective(name = DirectiveConstants.UPPERCASE_DIRECTIVE_NAME)
public class UppercaseDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        return value.toUpperCase();
    }
}