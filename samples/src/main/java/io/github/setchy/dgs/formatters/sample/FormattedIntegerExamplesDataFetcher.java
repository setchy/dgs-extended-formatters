package io.github.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;

@DgsComponent
public class FormattedIntegerExamplesDataFetcher {

    private static final Integer SAMPLE_DATA = 2023;

    @DgsData(parentType = "FormattedIntegerExamples", field = "original")
    @DgsData(parentType = "FormattedIntegerExamples", field = "absolute")
    @DgsData(parentType = "FormattedIntegerExamples", field = "ceiling")
    @DgsData(parentType = "FormattedIntegerExamples", field = "floored")
    public Integer exampleData() {
        return SAMPLE_DATA;
    }
}
