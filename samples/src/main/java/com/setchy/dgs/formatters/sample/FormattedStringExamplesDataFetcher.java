package com.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;

@DgsComponent
public class FormattedStringExamplesDataFetcher {

    private static final String SAMPLE_DATA = "   Example data    ";

    @DgsData(parentType = "FormattedStringExamples", field = "original")
    @DgsData(parentType = "FormattedStringExamples", field = "uppercased")
    @DgsData(parentType = "FormattedStringExamples", field = "lowercased")
    @DgsData(parentType = "FormattedStringExamples", field = "trimmed")
    @DgsData(parentType = "FormattedStringExamples", field = "trimmedUppercased")
    @DgsData(parentType = "FormattedStringExamples", field = "capitalized")
    @DgsData(parentType = "FormattedStringExamples", field = "reversed")
    @DgsData(parentType = "FormattedStringExamples", field = "swapcased")
    @DgsData(parentType = "FormattedStringExamples", field = "abbreviated")
    @DgsData(parentType = "FormattedStringExamples", field = "camelcased")
    public String exampleData(DgsDataFetchingEnvironment dfe) {
        return SAMPLE_DATA;
    }
}
