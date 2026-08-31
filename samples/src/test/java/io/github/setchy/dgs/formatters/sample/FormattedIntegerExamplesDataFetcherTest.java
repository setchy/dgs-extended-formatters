package io.github.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FormattedIntegerExamplesDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    private static final String QUERY = """
            {
                formattedIntegers {
                    original
                    absolute
                    ceiling
                    floored
                }
            }
            """;

    @Test
    void original() {
        assertEquals(2023,
                (Integer) dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedIntegers.original"));
    }

    @Test
    void absolute() {
        assertEquals(2023,
                (Integer) dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedIntegers.absolute"));
    }

    @Test
    void ceiling() {
        assertEquals(2023,
                (Integer) dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedIntegers.ceiling"));
    }

    @Test
    void floored() {
        assertEquals(2023,
                (Integer) dgsQueryExecutor.executeAndExtractJsonPath(QUERY, "data.formattedIntegers.floored"));
    }
}
