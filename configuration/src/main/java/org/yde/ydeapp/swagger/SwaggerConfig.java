package org.yde.ydeapp.swagger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select() //
            .apis(RequestHandlerSelectors.basePackage("org.yde.ydeapp.exposition"))
            .paths(Predicates.not(PathSelectors.regex("/error.*"))) //
            .build() //
            .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()//
            .title("Swagger cartographie applications service") //
            .description("No description provided") //
            .license("private use") //
            .licenseUrl("none") //
            .termsOfServiceUrl("") //
            .version("1.0") //
            .contact(new Contact("", "", "daniel@missud.net")) //
            .build();
    }

}