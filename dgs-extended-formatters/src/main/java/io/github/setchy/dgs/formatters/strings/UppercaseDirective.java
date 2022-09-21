package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import graphql.schema.GraphQLFieldDefinition;


@DgsDirective(name = DirectiveConstants.UPPERCASE_DIRECTIVE_NAME)
public class UppercaseDirective extends AbstractStringDirective {

    @Override
    public String format(GraphQLFieldDefinition field, String value) {
        return value.toUpperCase();
    }
}