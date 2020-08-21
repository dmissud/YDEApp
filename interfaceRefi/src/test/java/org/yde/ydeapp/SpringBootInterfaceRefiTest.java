package org.yde.ydeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan("org.yde.ydeapp")
public class SpringBootInterfaceRefiTest {
    public static void main(final String[] args) {
        SpringApplication.run(SpringBootInterfaceRefiTest.class, args);
    }
}
