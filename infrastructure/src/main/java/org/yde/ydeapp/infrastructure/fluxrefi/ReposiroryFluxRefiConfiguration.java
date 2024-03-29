package org.yde.ydeapp.infrastructure.fluxrefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReposiroryFluxRefiConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ReposiroryFluxRefiConfiguration.class);

    @Value("${ydeapp.dir}")
    private String baseDir;

    public ReposiroryFluxRefiConfiguration() {
        log.info("UploadDir = {}", baseDir);
    }

    public String getUploadDir() {
        return baseDir.concat("/upload");
    }

    public String getWorkDir() {
        return baseDir.concat("/work");
    }

    public String getStoreDir() {
        return baseDir.concat("/store");
    }

}
