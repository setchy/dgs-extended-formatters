package com.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import com.setchy.dgs.formatters.DirectiveConstants;
import graphql.schema.GraphQLFieldDefinition;
import org.apache.commons.text.WordUtils;


@DgsDirective(name = DirectiveConstants.UNCAPITALIZE_DIRECTIVE_NAME)
public class UncapitalizeDirective extends AbstractStringDirective {

    @Override
    public String format(GraphQLFieldDefinition field, String value) {
        return WordUtils.uncapitalize(value);
    }
}