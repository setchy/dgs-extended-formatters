package io.github.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FormattedFloatExamplesDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    private static final String QUERY = """
            {
                formattedFloats {
                    original
                    absolute
                    ceiling
                    floored
                }
            }
            """;

    @Test
    void original() {
        assertEquals(19453.432,
                (Double) dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedFloats.original"), 0.001);
    }

    @Test
    void absolute() {
        assertEquals(19453.432,
                (Double) dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedFloats.absolute"), 0.001);
    }

    @Test
    void ceiling() {
        assertEquals(19454.0,
                (Double) dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedFloats.ceiling"), 0.001);
    }

    @Test
    void floored() {
        assertEquals(19453.0,
                (Double) dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedFloats.floored"), 0.001);
    }
}
