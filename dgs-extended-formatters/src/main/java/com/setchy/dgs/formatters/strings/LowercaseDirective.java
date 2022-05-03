package com.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import com.setchy.dgs.formatters.DirectiveConstants;


@DgsDirective(name = DirectiveConstants.LOWERCASE_DIRECTIVE_NAME)
public class LowercaseDirective extends StringDirective {

    @Override
    public String format(Object value) {
        return ((String) value).toLowerCase();
    }
}