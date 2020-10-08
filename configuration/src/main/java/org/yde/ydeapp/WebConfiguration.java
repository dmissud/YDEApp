package org.yde.ydeapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Value("${ydeapp.client}")
    private String clientUrlDir;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(clientUrlDir, "http://172.24.1.106:4201");
//            .allowedOrigins("*/**"); // for /** means all mapping URL, and * for all domain
    }
}
