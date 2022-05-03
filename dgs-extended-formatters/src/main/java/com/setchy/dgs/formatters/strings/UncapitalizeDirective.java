package com.setchy.dgs.formatters.strings;

import com.netflix.graphql.dgs.DgsDirective;
import com.setchy.dgs.formatters.DirectiveConstants;
import org.apache.commons.text.WordUtils;


@DgsDirective(name = DirectiveConstants.UNCAPITALIZE_DIRECTIVE_NAME)
public class UncapitalizeDirective extends StringDirective {

    @Override
    public String format(Object value) {
        return WordUtils.uncapitalize((String) value);
    }
}