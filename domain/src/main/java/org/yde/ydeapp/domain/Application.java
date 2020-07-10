package org.yde.ydeapp.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final String codeApplication;
    private String shortDescription;
    private String longDescription;
    private String nameOfResponsable;

    private Application(String codeApplication) {
        this.codeApplication = codeApplication;
    }

    public String getCodeApplication() { return this.codeApplication; }

    public String getShortDescription() { return shortDescription; }

    public String getLongDescription() { return longDescription; }

    public String getNameOfResponsable() { return nameOfResponsable; }

    public static class Builder {
        private final String codeApplication;
        private String shortDescription = "to be completed";
        private String longDescription = "to be completed";
        private String nameOfResponsable = "who is responsible ?";

        public Builder(@NotNull String codeApplication) {
            this.codeApplication = codeApplication;
            log.trace("New builder Application");
        }

        public Builder withShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder withLongDescription(String longDescription) {
            this.longDescription = longDescription;
            return this;
        }

        public Builder withResponsable(String nameOfResponsable) {
            this.nameOfResponsable = nameOfResponsable;
            return this;
        }

        public Application build() {
            Application application = new Application(this.codeApplication);
            application.shortDescription = this.shortDescription;
            application.longDescription = this.longDescription;
            application.nameOfResponsable = nameOfResponsable;
            log.trace("New Application Create");
            return application;
        }
    }
}
