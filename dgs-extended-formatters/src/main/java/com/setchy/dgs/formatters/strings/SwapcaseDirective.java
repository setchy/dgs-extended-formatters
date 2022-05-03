package com.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import com.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.lang3.StringUtils;


@DgsDirective(name = DirectiveConstants.SWAPCASE_DIRECTIVE_NAME)
public class SwapcaseDirective extends StringDirective {

    @Override
    public String format(Object value) {
        return StringUtils.swapCase((String) value);
    }
}