package com.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import com.setchy.dgs.formatters.DirectiveConstants;
import graphql.schema.GraphQLFieldDefinition;


@DgsDirective(name = DirectiveConstants.LOWERCASE_DIRECTIVE_NAME)
public class LowercaseDirective extends StringDirective {

    @Override
    public String format(GraphQLFieldDefinition field, String value) {
        return value.toLowerCase();
    }
}