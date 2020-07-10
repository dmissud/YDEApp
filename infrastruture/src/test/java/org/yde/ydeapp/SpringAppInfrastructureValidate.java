package org.yde.ydeapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "org.yde.ydeapp")
@EnableJpaRepositories
public class SpringAppInfrastructureValidate {
}
