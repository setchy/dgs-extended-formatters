package io.github.setchy.dgs.formatters.exception;

import io.github.setchy.dgs.formatters.DirectiveConstants;

public class ExceptionUtils {
    public static final String formatExceptionMessage(String directiveName, String argumentName) {
        return String.format("'%s' formatter directive missing required argument '%s'", directiveName, argumentName);
    }
}
