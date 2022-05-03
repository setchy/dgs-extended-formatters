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

        public String getUppercased() {
            return sample;
        }

        public String getLowercased() {
            return sample;
        }

        public String getTrimmed() {
            return sample;
        }

        public String getTrimmedUppercased() {
            return sample;
        }

        public String getCapitalized() {
            return sample;
        }

        public String getReversed() {
            return sample;
        }

        public String getSwapcased() {
            return sample;

        }

        public String getUncapitalized() {
            return sample;
        }

        public String getAbbreviated() {
            return sample;
        }
    }
}
