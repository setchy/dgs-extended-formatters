package io.github.setchy.dgs.formatters.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.github.setchy")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
