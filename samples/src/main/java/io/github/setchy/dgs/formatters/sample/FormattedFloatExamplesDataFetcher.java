package io.github.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;

@DgsComponent
public class FormattedFloatExamplesDataFetcher {

    private static final Float SAMPLE_DATA = 19453.432f;

    @DgsData(parentType = "FormattedFloatExamples", field = "original")
    @DgsData(parentType = "FormattedFloatExamples", field = "absolute")
    @DgsData(parentType = "FormattedFloatExamples", field = "ceiling")
    @DgsData(parentType = "FormattedFloatExamples", field = "floored")
    public Float exampleData(DgsDataFetchingEnvironment dfe) {
        return SAMPLE_DATA;
    }
}
