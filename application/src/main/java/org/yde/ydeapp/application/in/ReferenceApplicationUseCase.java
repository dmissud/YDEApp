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

        @Pattern(regexp = "^([0-9]{8})$")
        private final String idRefOrganizationMoe;

        private final ResponsableCmd responsableCmd;

        public ReferenceApplicationCmd(String codeApp,
                                       String shortDescription,
                                       String longDescription,
                                       ResponsableCmd responsableCmd,
                                       String idRefOrganizationMoe) {
            this.codeApp = codeApp;
            this.shortDescription = shortDescription;
            this.longDescription = longDescription;
            this.responsableCmd = responsableCmd;
            this.idRefOrganizationMoe= idRefOrganizationMoe;

        }
        @Override
        public void validate() {
            super.validate();
            responsableCmd.validate();
        }

        public String getCodeApp() { return codeApp; }

        public String getShortDescription() { return shortDescription; }

        public String getLongDescription() { return longDescription; }

        public String getUid() {
            return responsableCmd.getUid();
        }

        public String getFirstName() {
            return responsableCmd.getFirstName();
        }

        public String getLastName() {
            return responsableCmd.getLastName();
        }

        public String getIdRefOrganizationMoe() {
            return idRefOrganizationMoe;
        }

        /*
         * Commande to create the Responsable
         */
        public static class ResponsableCmd extends SelfValidating<ResponsableCmd> {

            private final String uid;
            private final String firstName;
            private final String lastName;

            public ResponsableCmd(String uid, String firstName, String lastName)  {
                this.uid = uid;
                this.firstName = firstName;
                this.lastName = lastName;
            }

            public String getUid() {
                return uid;
            }

            public String getFirstName() {
                return firstName;
            }

            public String getLastName() {
                return lastName;
            }
        }
    }
}
