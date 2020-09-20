package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(name = "ydeappProperties", value = "classpath:ydeapp.properties")
public class YdeAppReposiroryConfiguration {
    private static final Logger log = LoggerFactory.getLogger(YdeAppReposiroryConfiguration.class);

    @Value("${ydeapp.upload.dir}")
    private String uploadDir;

    public YdeAppReposiroryConfiguration() {
        log.info("UploadDir = {}", uploadDir);
    }

    public String getUploadDir() {
        return uploadDir;
    }

}
