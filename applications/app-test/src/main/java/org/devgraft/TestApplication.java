package org.devgraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import support.exception.EnableException;
import support.response.EnableServletResponse;

@EnableException
@EnableServletResponse
@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
