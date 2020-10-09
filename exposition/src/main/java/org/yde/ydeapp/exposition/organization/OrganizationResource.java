package org.yde.ydeapp.exposition.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.organization.OrganizationQuery;
import org.yde.ydeapp.application.in.organization.ReferenceOrganizationUseCase;
import org.yde.ydeapp.application.in.organization.ReferenceOrganizationUseCase.ReferenceOrganisationCmd;
import org.yde.ydeapp.domain.organization.Organization;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/api/V1")
public class OrganizationResource {
    @Autowired
    ReferenceOrganizationUseCase referenceOrganizationUseCase;

    @Autowired
    OrganizationQuery organizationQuery;

    @Secured("ROLE_ADMIN")
    @PostMapping("organizations")
    public ResponseEntity<Void> referenceOrganization(@Valid @RequestBody ReferenceOrganisationCmd referenceOrganisationCmd) {
        Organization organization = referenceOrganizationUseCase.referenceOrganization(referenceOrganisationCmd);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{nameOrganization}")
            .buildAndExpand(organization.getIdRefog())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "organizations/{idRefog}", produces = {"application/json"})
    public ResponseEntity<Organization> retrieveOrganization(@Valid @NotNull @PathVariable("idRefog") final String idRefog) {
        Organization organization = organizationQuery.getOrganizationTree(idRefog);

        return new ResponseEntity<>(organization, HttpStatus.OK);
    }

}
