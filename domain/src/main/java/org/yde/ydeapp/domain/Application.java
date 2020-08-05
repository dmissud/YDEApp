package org.yde.ydeapp.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final String codeApplication;
    private String shortDescription;
    private String longDescription;
    private Personne responsable;

    private Application(String codeApplication) {
        this.codeApplication = codeApplication;
    }

    public String getCodeApplication() { return this.codeApplication; }

    public String getShortDescription() { return shortDescription; }

    public String getLongDescription() { return longDescription; }

    public Personne getResponsable() { return responsable; }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setResponsable(Personne responsable) {
        this.responsable = responsable;
    }

    public static class Builder {
        private final String codeApplication;
        private String shortDescription = "to be completed";
        private String longDescription = "to be completed";
        private Personne responsable = null;

        public ApplicationIdent giveApplicationIdent() {
            return new ApplicationIdent(this.codeApplication, this.shortDescription);
        }

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

        public Builder withResponsable(Personne responsable) {
            this.responsable = responsable;
            return this;
        }

        public Application build() {
            Application application = new Application(this.codeApplication);
            application.shortDescription = this.shortDescription;
            application.longDescription = this.longDescription;
            application.responsable = this.responsable;
            log.trace("New Application Create");
            return application;
        }
    }
}
