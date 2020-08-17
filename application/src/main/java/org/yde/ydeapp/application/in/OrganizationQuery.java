package org.yde.ydeapp.application.in;

import org.yde.ydeapp.domain.Organization;

import java.util.List;

public interface OrganizationQuery {
    Organization getOrganization(String idRefog);

    List<Organization> getAllOrganizationsRoot();
}
