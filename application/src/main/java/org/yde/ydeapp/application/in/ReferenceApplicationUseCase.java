package org.yde.ydeapp.application.in;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.Application;

import javax.validation.constraints.Pattern;

@FunctionalInterface
public interface ReferenceApplicationUseCase {
    Application referenceApplication(ReferenceApplicationCmd referenceApplicationCmd);

    @Validated
    class ReferenceApplicationCmd extends SelfValidating<ReferenceApplicationCmd> {

        @Pattern(regexp = "^(AP[0-9]{5})$")
        private final String codeApp;

        private final String shortDescription;

        private final String longDescription;

        private final String nameOfResponsable;

        public ReferenceApplicationCmd(@Pattern(regexp = "^(AP[0-9]{5})$") String codeApp,
                                       String shortDescription,
                                       String longDescription,
                                       String nameOfResponsable) {
            this.codeApp = codeApp;
            this.shortDescription = shortDescription;
            this.longDescription = longDescription;
            this.nameOfResponsable = nameOfResponsable;

            this.validateSelf();
        }

        public String getCodeApp() { return codeApp; }

        public String getShortDescription() { return shortDescription; }

        public String getLongDescription() { return longDescription; }

        public String getNameOfResponsable() { return nameOfResponsable; }
    }
}
