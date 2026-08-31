package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;


/** Lowercases all characters in a String. */
@DgsDirective(name = DirectiveConstants.LOWERCASE_DIRECTIVE_NAME)
public class LowercaseDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        return value.toLowerCase();
    }
}