package io.github.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.GraphQLFieldDefinition;
import io.github.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.text.CaseUtils;


@DgsDirective(name = DirectiveConstants.CAMELCASE_DIRECTIVE_NAME)
public class CamelcaseDirective extends AbstractStringDirective {

    @Override
    public String format(GraphQLFieldDefinition field, String value) {
        return CaseUtils.toCamelCase(value, false, ' ');
    }
}