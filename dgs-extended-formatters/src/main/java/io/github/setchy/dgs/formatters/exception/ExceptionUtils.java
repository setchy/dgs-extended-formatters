package io.github.setchy.dgs.formatters.exception;


/** Helper for building consistent directive-related exception messages. */
public class ExceptionUtils {

    /**
     * Builds a message describing that a directive's required argument is missing.
     *
     * @param directiveName the name of the directive (without the leading {@code @})
     * @param argumentName  the name of the missing required argument
     * @return a formatted error message
     */
    public static final String formatExceptionMessage(String directiveName, String argumentName) {
        return String.format("'%s' formatter directive missing required argument '%s'", directiveName, argumentName);
    }

    private ExceptionUtils(){
    }
}
