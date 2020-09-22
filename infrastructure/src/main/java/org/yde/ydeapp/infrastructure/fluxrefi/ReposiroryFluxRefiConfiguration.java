package org.yde.ydeapp.infrastructure.fluxrefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(name = "ydeappProperties", value = "classpath:ydeapp.properties")
public class ReposiroryFluxRefiConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ReposiroryFluxRefiConfiguration.class);

    @Value("${ydeapp.upload.dir}")
    private String uploadDir;

    @Value("${ydeapp.old.dir}")
    private String oldDir;

    public ReposiroryFluxRefiConfiguration() {
        log.info("UploadDir = {}", uploadDir);
    }

    public String getUploadDir() {
        return uploadDir;
    }
    public String getOldDir() {
        return oldDir;
    }


}
