package org.yde.ydeapp.application.in;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.Organization;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public interface ReferenceOrganizationUseCase {
    Organization referenceOrganization(ReferenceOrganisationCmd referenceOrganisationCmd);

    @Validated
    class ReferenceOrganisationCmd extends SelfValidating<ReferenceOrganisationCmd> {

        @Size(min = 3, max = 100)
        private final String organizationName;

        @Size(min = 8, max = 8)
        @Pattern(regexp = "^([0-9]{8})$")
        private final String idRefog;

        private final List<ReferenceOrganisationCmd> children;

        public ReferenceOrganisationCmd(String organizationName,
                                        String idRefog,
                                        List<ReferenceOrganisationCmd> children) {
            this.organizationName = organizationName;
            this.idRefog = idRefog;
            this.children = children;
        }

        @Override
        public void validate() {
            super.validate();
            if (this.children != null) {
                this.children.forEach(ReferenceOrganisationCmd::validate);
            }
        }
        public String getOrganizationName() {
            return organizationName;
        }
        public String getIdRefog() { return idRefog; }
        public List<ReferenceOrganisationCmd> getChildren() {
            return children;
        }
    }
}
