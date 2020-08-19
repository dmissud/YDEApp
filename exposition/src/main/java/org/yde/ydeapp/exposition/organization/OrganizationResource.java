package org.yde.ydeapp.exposition.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.OrganizationQuery;
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase;
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase.ReferenceOrganisationCmd;
import org.yde.ydeapp.domain.Organization;

import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class OrganizationResource {
    @Autowired
    ReferenceOrganizationUseCase referenceOrganizationUseCase;

    @Autowired
    OrganizationQuery organizationQuery;

    @PostMapping("organizations")
    public ResponseEntity<Void> referenceOrganization(@RequestBody ReferenceOrganisationCmd referenceOrganisationCmd) {
        Organization organization = referenceOrganizationUseCase.referenceOrganization(referenceOrganisationCmd);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{nameOrganization}")
            .buildAndExpand(organization.getIdRefog())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "organizations/{idRefog}", produces = {"application/json"})
    public ResponseEntity<Organization> retrieveOrganization(@NotNull @PathVariable("idRefog") final String idRefog) {
        Organization organization = organizationQuery.getOrganizationTree(idRefog);

        return new ResponseEntity<>(organization, HttpStatus.OK);
    }
}
