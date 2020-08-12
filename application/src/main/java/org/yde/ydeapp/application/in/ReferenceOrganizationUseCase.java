package org.yde.ydeapp.application.in;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;
import org.yde.ydeapp.domain.Organization;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public interface ReferenceOrganizationUseCase {
    Organization referenceOrganization(ReferenceOrganisationCmd referenceOrganisationCmd);

    @Validated
    class ReferenceOrganisationCmd extends SelfValidating<ReferenceOrganisationCmd> {

        @Size(min = 3, max = 100)
        private final String organizationName;

        @NotNull
        private final List<ReferenceOrganisationCmd> children;

        public ReferenceOrganisationCmd(@Size(min = 5, max = 100) String organizationName,
                                        @NotNull List<ReferenceOrganisationCmd> children) {
            this.organizationName = organizationName;
            this.children = children;

            this.validateSelf();
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public List<ReferenceOrganisationCmd> getChildren() {
            return children;
        }
    }
}
