package io.github.setchy.dgs.formatters.sample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
        // Intentionally empty: if the Spring/DGS application context (schema loading,
        // @DgsComponent registration, directive wiring) fails to start, this test fails.
    }
}
