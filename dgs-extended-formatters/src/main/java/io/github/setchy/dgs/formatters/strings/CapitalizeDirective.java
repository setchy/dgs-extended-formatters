package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import graphql.schema.GraphQLFieldDefinition;
import org.apache.commons.text.WordUtils;


@DgsDirective(name = DirectiveConstants.CAPITALIZE_DIRECTIVE_NAME)
public class CapitalizeDirective extends AbstractStringDirective {

    @Override
    public String format(GraphQLFieldDefinition field, String value) {
        return WordUtils.capitalize(value);
    }
}