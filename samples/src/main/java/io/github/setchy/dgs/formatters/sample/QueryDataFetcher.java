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
    public FormattedIntegerExamples formattedIntegers() {
        return new FormattedIntegerExamples();
    }

    @DgsQuery
    public FormattedFloatExamples formattedFloats() {
        return new FormattedFloatExamples();
    }

    public class FormattedStringExamples {
    }

    public class FormattedIntegerExamples {
    }

    public class FormattedFloatExamples {
    }
}
