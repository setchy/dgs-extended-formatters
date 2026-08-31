package io.github.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FormattedStringExamplesDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    private static final String QUERY = """
            {
                formattedStrings {
                    original
                    uppercased
                    lowercased
                    trimmed
                    trimmedUppercased
                    capitalized
                    reversed
                    swapcased
                    abbreviated
                    camelcased
                    resourceId
                    prefixed
                    suffixed
                    encoded
                }
            }
            """;

    @Test
    void original() {
        assertEquals("   Example data    ",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.original"));
    }

    @Test
    void uppercased() {
        assertEquals("   EXAMPLE DATA    ",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.uppercased"));
    }

    @Test
    void lowercased() {
        assertEquals("   example data    ",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.lowercased"));
    }

    @Test
    void trimmed() {
        assertEquals("Example data",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.trimmed"));
    }

    @Test
    void trimmedUppercased() {
        assertEquals("EXAMPLE DATA",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.trimmedUppercased"));
    }

    @Test
    void capitalized() {
        assertEquals("   Example Data    ",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.capitalized"));
    }

    @Test
    void reversed() {
        assertEquals("    atad elpmaxE   ",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.reversed"));
    }

    @Test
    void swapcased() {
        assertEquals("   eXAMPLE DATA    ",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.swapcased"));
    }

    @Test
    void abbreviated() {
        assertEquals("   Exam...",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.abbreviated"));
    }

    @Test
    void camelcased() {
        assertEquals("exampleData",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.camelcased"));
    }

    @Test
    void resourceId() {
        assertEquals("CgZkb21haW4SCXN1YmRvbWFpbhoKc3lzdGVtTmFtZSITICAgRXhhbXBsZSBkYXRhICAgIA==",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.resourceId"));
    }

    @Test
    void prefixed() {
        assertEquals("prefix:   Example data    ",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.prefixed"));
    }

    @Test
    void suffixed() {
        assertEquals("   Example data    - suffix",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.suffixed"));
    }

    @Test
    void encoded() {
        assertEquals("ICAgRXhhbXBsZSBkYXRhICAgIA==",
                dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedStrings.encoded"));
    }
}
