package com.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import com.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.text.WordUtils;


@DgsDirective(name = DirectiveConstants.CAPITALIZE_DIRECTIVE_NAME)
public class CapitalizeDirective extends StringDirective {

    @Override
    public String format(Object value) {
        return WordUtils.capitalize((String) value);
    }
}