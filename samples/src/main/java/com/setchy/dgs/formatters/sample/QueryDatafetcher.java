package com.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class QueryDatafetcher {

    @DgsQuery
    public FormattedStringExamples formattedStrings() {
        return new FormattedStringExamples();
    }


    public class FormattedStringExamples {
        private final String sample = "   Example data    ";

        public String getOriginal() {
            return sample;
        }

        public String getUppercase() {
            return sample;
        }

        public String getLowercase() {
            return sample;
        }

        public String getTrimmed() {
            return sample;
        }

        public String getTrimmedUppercase() {
            return sample;
        }
    }
}
