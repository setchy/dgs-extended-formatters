package com.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import com.setchy.dgs.formatters.DirectiveConstants;
import graphql.schema.GraphQLFieldDefinition;


@DgsDirective(name = DirectiveConstants.TRIM_DIRECTIVE_NAME)
public class TrimDirective extends AbstractStringDirective {

    @Override
    public String format(GraphQLFieldDefinition field, String value) {
        return value.trim();
    }
}