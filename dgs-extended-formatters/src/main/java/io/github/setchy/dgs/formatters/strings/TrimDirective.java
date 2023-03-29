package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;


@DgsDirective(name = DirectiveConstants.TRIM_DIRECTIVE_NAME)
public class TrimDirective extends AbstractStringDirective {

    @Override
    public String format(GraphQLFieldDefinition field, String value) {
        return value.trim();
    }
}