package com.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class QueryDataFetcher {

    @DgsQuery
    public FormattedStringExamples formattedStrings() {
        return new FormattedStringExamples();
    }


    public class FormattedStringExamples {
    }
}
