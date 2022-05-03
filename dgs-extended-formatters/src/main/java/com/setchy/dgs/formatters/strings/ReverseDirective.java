package com.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import com.setchy.dgs.formatters.DirectiveConstants;
import graphql.schema.GraphQLFieldDefinition;
import org.apache.commons.lang3.StringUtils;


@DgsDirective(name = DirectiveConstants.REVERSE_DIRECTIVE_NAME)
public class ReverseDirective extends StringDirective {

    @Override
    public String format(GraphQLFieldDefinition field, String value) {
        return StringUtils.reverse(value);
    }
}