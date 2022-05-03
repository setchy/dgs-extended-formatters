package com.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import com.setchy.dgs.formatters.DirectiveConstants;


@DgsDirective(name = DirectiveConstants.UPPERCASE_DIRECTIVE_NAME)
public class UppercaseDirective extends StringDirective {

    @Override
    public String format(Object value) {
        System.out.println("here");
        return ((String) value).toUpperCase();
    }
}