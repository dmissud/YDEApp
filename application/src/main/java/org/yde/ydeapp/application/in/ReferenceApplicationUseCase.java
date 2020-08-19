package org.yde.ydeapp.application.in;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.Application;

import javax.validation.constraints.Pattern;

public interface ReferenceApplicationUseCase {
    ResultOfCollection referenceOrUpdateCollectionOfApplication(CollectionApplicationCmd collectionApplicationCmd);
    StateCmdEnum referenceOrUpdateApplication(ReferenceApplicationCmd referenceApplicationCmd);
    Application updateApplication(String codeApplication, ReferenceApplicationCmd referenceApplicationCmd);

    @Validated
    class ReferenceApplicationCmd extends SelfValidating<ReferenceApplicationCmd> {

        @Pattern(regexp = "^(AP[0-9]{5})$")
        private final String codeApp;

        private final String shortDescription;

        private final String longDescription;

        private final String uid;

        private final String firstName;

        private final String lastName;

        private final String idRefOrganizationMoe;

        public ReferenceApplicationCmd(@Pattern(regexp = "^(AP[0-9]{5})$") String codeApp,
                                       String shortDescription,
                                       String longDescription,
                                       String uid,
                                       String firstName,
                                       String lastName,
                                       String idRefOrganizationMoe) {
            this.codeApp = codeApp;
            this.shortDescription = shortDescription;
            this.longDescription = longDescription;
            this.uid = uid;
            this.firstName = firstName;
            this.lastName = lastName;
            this.idRefOrganizationMoe= idRefOrganizationMoe;


        }

        public String getCodeApp() { return codeApp; }

        public String getShortDescription() { return shortDescription; }

        public String getLongDescription() { return longDescription; }

        public String getUid() {
            return uid;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getIdRefOrganizationMoe() {
            return idRefOrganizationMoe;
        }
    }
}
