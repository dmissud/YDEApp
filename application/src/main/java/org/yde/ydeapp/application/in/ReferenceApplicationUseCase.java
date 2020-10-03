package org.yde.ydeapp.application.in;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.application.Application;
import org.yde.ydeapp.domain.flux.StateUpdateEnum;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public interface ReferenceApplicationUseCase {
    StateUpdateEnum referenceOrUpdateApplication(ReferenceApplicationCmd referenceApplicationCmd);
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

        private final CycleLifeCmd cycleLifeCmd;

        private final ItSolutionCmd itSolutionCmd;

        private final CriticityCmd criticityCmd;

        public ReferenceApplicationCmd(String codeApp,
                                       String shortDescription,
                                       String longDescription,
                                       ResponsableCmd responsableCmd,
                                       String idRefOrganizationMoe,
                                       CycleLifeCmd cycleLifeCmd,
                                       ItSolutionCmd itSolutionCmd,
                                       CriticityCmd criticityCmd) {
            this.codeApp = codeApp;
            this.shortDescription = shortDescription;
            this.longDescription = longDescription;
            this.idRefOrganizationMoe = idRefOrganizationMoe;
            this.responsableCmd = responsableCmd;
            this.cycleLifeCmd = cycleLifeCmd;
            this.itSolutionCmd = itSolutionCmd;
            this.criticityCmd = criticityCmd;
        }

        @Override
        public void validate() {
            super.validate();
            responsableCmd.validate();
            cycleLifeCmd.validate();
            itSolutionCmd.validate();
            criticityCmd.validate();
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
         * Cycle life
         */

        public String getState() { return cycleLifeCmd.getState();
        }

        public LocalDate getDateOfCreation() {
            return cycleLifeCmd.dateOfCreation;
        }

        public LocalDate getDateOfLastUpdate() {
            return cycleLifeCmd.dateOfLastUpdate;
        }

        public LocalDate getDateEndInReality() {
            return cycleLifeCmd.dateEndInReality;
        }

        /*
        *  It solution
         */
        public String getTypeOfSolution() {
            return itSolutionCmd.typeOfSolution;
        }

        public String getNameOfFirmware() {
            return itSolutionCmd.nameOfFirmware;
        }

        public String getLabelOfSourcingMode() {
            return itSolutionCmd.labelOfSourcingMode;
        }
        /*
         *  criticity
         */
        public String getPrivilegeInformation(){
            return criticityCmd.privilegeInformation;
        }
        public String getPersonalData(){
            return criticityCmd.personalData;
        }
        public String getServiceClass(){
            return criticityCmd.serviceClass;
        }
        public String getAvailability(){
            return criticityCmd.aviability;
        }
        public String getRpo(){
            return criticityCmd.rpo;
        }
        public String getRto(){
            return criticityCmd.rto;
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

        /*
         * commande to create cycle life
         */

        public static class CycleLifeCmd extends SelfValidating<CycleLifeCmd> {
            private final String state;
            private final LocalDate dateOfCreation;
            private final LocalDate dateOfLastUpdate;
            private final LocalDate dateEndInReality;

            public CycleLifeCmd(String state, LocalDate dateOfCreation, LocalDate dateOfLastUpdate, LocalDate dateEndInReality) {
                this.state = state;
                this.dateOfCreation = dateOfCreation;
                this.dateOfLastUpdate = dateOfLastUpdate;
                this.dateEndInReality = dateEndInReality;
            }

            public String getState() {
                return state;
            }

            public LocalDate getDateOfCreation() {
                return dateOfCreation;
            }

            public LocalDate getDateOfLastUpdate() {
                return dateOfLastUpdate;
            }

            public LocalDate getDateEndInReality() {
                return dateEndInReality;
            }
        }

        /*
         * commande to create ItSolution
         */
        public static class ItSolutionCmd extends SelfValidating<ItSolutionCmd>{
            private final String typeOfSolution;
            private final String nameOfFirmware;
            private final String labelOfSourcingMode;

            public ItSolutionCmd(String typeOfSolution, String nameOfFirmware, String labelOfSourcingMode) {
                this.typeOfSolution = typeOfSolution;
                this.nameOfFirmware = nameOfFirmware;
                this.labelOfSourcingMode = labelOfSourcingMode;
            }

            public String getTypeOfSolution() {
                return typeOfSolution;
            }

            public String getNameOfFirmware() {
                return nameOfFirmware;
            }

            public String getLabelOfSourcingMode() {
                return labelOfSourcingMode;
            }
        }


        /*
         * commande to create criticityCmd
         */
        public static class CriticityCmd extends SelfValidating<CriticityCmd>{
            private final String privilegeInformation;
            private final String personalData;
            private final String serviceClass;
            private final String aviability;
            private final String rpo;
            private final String rto;

            public CriticityCmd(String privilegeInformation, String personalData, String serviceClass, String aviability, String rpo, String rto) {
                this.privilegeInformation = privilegeInformation;
                this.personalData = personalData;
                this.serviceClass = serviceClass;
                this.aviability = aviability;
                this.rpo = rpo;
                this.rto = rto;
            }

            public String getPrivilegeInformation() {
                return privilegeInformation;
            }

            public String getPersonalData() {
                return personalData;
            }

            public String getServiceClass() {
                return serviceClass;
            }

            public String getAviability() {
                return aviability;
            }

            public String getRpo() {
                return rpo;
            }

            public String getRto() {
                return rto;
            }
        }
    }
}
