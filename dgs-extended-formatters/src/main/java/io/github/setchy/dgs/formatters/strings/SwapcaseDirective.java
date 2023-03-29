package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.lang3.StringUtils;


@DgsDirective(name = DirectiveConstants.SWAPCASE_DIRECTIVE_NAME)
public class SwapcaseDirective extends AbstractStringDirective {

    @Override
    public String format(GraphQLFieldDefinition field, String value) {
        return StringUtils.swapCase(value);
    }
}