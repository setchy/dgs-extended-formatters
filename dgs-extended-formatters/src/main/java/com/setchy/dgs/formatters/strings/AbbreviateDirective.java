package com.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import com.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.lang3.StringUtils;


@DgsDirective(name = DirectiveConstants.ABBREVIATE_DIRECTIVE_NAME)
public class AbbreviateDirective extends StringDirective {

    @Override
    public String format(Object value) {
        // TODO - Make this a directive argument
        return StringUtils.abbreviate((String) value, 5);
    }
}