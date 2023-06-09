package io.github.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class QueryDataFetcher {

    @DgsQuery
    public FormattedStringExamples formattedStrings() {
        return new FormattedStringExamples();
    }

    @DgsQuery
    public FormattedArgsExamples formattedArgs() {
        return new FormattedArgsExamples();
    }

    @DgsQuery
    public FormattedIntegerExamples formattedIntegers() {
        return new FormattedIntegerExamples();
    }

    @DgsQuery
    public FormattedFloatExamples formattedFloats() {
        return new FormattedFloatExamples();
    }

    public static class FormattedStringExamples {
    }

    public static class FormattedArgsExamples {
    }

    public static class FormattedIntegerExamples {
    }

    public static class FormattedFloatExamples {
    }
}
