package org.yde.ydeapp.application.in;

import org.yde.ydeapp.domain.Organization;

public interface OrganizationQuery {
    Organization getOrganizationTree(String idRefog);
}
