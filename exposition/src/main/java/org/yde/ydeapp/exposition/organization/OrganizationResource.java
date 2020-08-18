package org.yde.ydeapp.exposition.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase;
import org.yde.ydeapp.application.in.ReferenceOrganizationUseCase.ReferenceOrganisationCmd;
import org.yde.ydeapp.domain.Organization;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class OrganizationResource {
    @Autowired
    ReferenceOrganizationUseCase referenceOrganizationUseCase;

    @PostMapping("/organizations")
    public ResponseEntity<Void> referenceOrganization(@RequestBody ReferenceOrganisationCmd referenceOrganisationCmd) {
        Organization organization = referenceOrganizationUseCase.referenceOrganization(referenceOrganisationCmd);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{nameOrganization}")
            .buildAndExpand(organization.getIdRefog())
            .toUri();

        return ResponseEntity.created(location).build();
    }

//    @GetMapping("/organizations")
//    public ResponseEntity<Void> retrieveAllOrganization() {
//        //Organization organization = referenceOrganizationUseCase.referenceOrganization();
//        URI location = ServletUriComponentsBuilder
//            .fromCurrentRequest()
//            .path("/{nameOrganization}")
//            .buildAndExpand(organization.getName())
//            .toUri();
//
//        return ResponseEntity.created(location).build();
//    }
}
