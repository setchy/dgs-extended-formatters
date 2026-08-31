package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.text.WordUtils;


/** Capitalizes the starting letter of each word in a String. */
@DgsDirective(name = DirectiveConstants.CAPITALIZE_DIRECTIVE_NAME)
public class CapitalizeDirective extends AbstractStringDirective {

    @Override
    public String applyFormatting(GraphQLFieldDefinition field, String value) {
        return WordUtils.capitalize(value);
    }
}