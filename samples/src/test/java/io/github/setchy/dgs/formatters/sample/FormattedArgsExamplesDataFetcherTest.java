package io.github.setchy.dgs.formatters.sample;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FormattedArgsExamplesDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void original() {
        String query = "{ formattedArgs { original(arg: \"  Hello World  \") } }";
        assertEquals("  Hello World  ",
                dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.original"));
    }

    @Test
    void uppercased() {
        String query = "{ formattedArgs { uppercased(arg: \"hello\") } }";
        assertEquals("HELLO",
                dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.uppercased"));
    }

    @Test
    void lowercased() {
        String query = "{ formattedArgs { lowercased(arg: \"HELLO\") } }";
        assertEquals("hello",
                dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.lowercased"));
    }

    @Test
    void trimmed() {
        String query = "{ formattedArgs { trimmed(arg: \"  hi  \") } }";
        assertEquals("hi",
                dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.trimmed"));
    }

    @Test
    void trimmedUppercased() {
        String query = "{ formattedArgs { trimmedUppercased(arg: \"  hi  \") } }";
        assertEquals("HI",
                dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.trimmedUppercased"));
    }

    @Test
    void capitalized() {
        String query = "{ formattedArgs { capitalized(arg: \"hello world\") } }";
        assertEquals("Hello World",
                dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.capitalized"));
    }

    @Test
    void reversed() {
        String query = "{ formattedArgs { reversed(arg: \"hello\") } }";
        assertEquals("olleh",
                dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.reversed"));
    }

    @Test
    void swapcased() {
        String query = "{ formattedArgs { swapcased(arg: \"Hello\") } }";
        assertEquals("hELLO",
                dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.swapcased"));
    }

    @Test
    void camelcased() {
        String query = "{ formattedArgs { camelcased(arg: \"hello world\") } }";
        assertEquals("helloWorld",
                dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.camelcased"));
    }

    @Test
    void encoded() {
        String query = "{ formattedArgs { encoded(arg: \"hello\") } }";
        assertEquals("aGVsbG8=",
                dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.encoded"));
    }

    @Test
    void absoluteInt() {
        String query = "{ formattedArgs { absoluteInt(arg: -7) } }";
        assertEquals(7,
                (Integer) dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.absoluteInt"));
    }

    @Test
    void ceilingInt() {
        String query = "{ formattedArgs { ceilingInt(arg: 3) } }";
        assertEquals(3,
                (Integer) dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.ceilingInt"));
    }

    @Test
    void flooredInt() {
        String query = "{ formattedArgs { flooredInt(arg: 3) } }";
        assertEquals(3,
                (Integer) dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.flooredInt"));
    }

    @Test
    void absoluteFloat() {
        String query = "{ formattedArgs { absoluteFloat(arg: -2.5) } }";
        assertEquals(2.5,
                (Double) dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.absoluteFloat"));
    }

    @Test
    void ceilingFloat() {
        String query = "{ formattedArgs { ceilingFloat(arg: 2.1) } }";
        assertEquals(3.0,
                (Double) dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.ceilingFloat"));
    }

    @Test
    void flooredFloat() {
        String query = "{ formattedArgs { flooredFloat(arg: 2.9) } }";
        assertEquals(2.0,
                (Double) dgsQueryExecutor.executeAndExtractJsonPath(query, "data.formattedArgs.flooredFloat"));
    }
}
