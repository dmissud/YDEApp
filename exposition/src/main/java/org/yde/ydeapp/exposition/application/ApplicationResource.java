package org.yde.ydeapp.exposition.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.GetApplicationQuery;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase.ReferenceApplicationCmd;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.ApplicationIdent;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationResource {

    @Autowired
    ReferenceApplicationUseCase referenceApplicationUseCase;

    @Autowired
    GetApplicationQuery getApplicationQuery;

    @PostMapping("applications")
    public ResponseEntity<Void> referenceApplication(@RequestBody ApplicationDesc applicationDesc) {

        ReferenceApplicationCmd referenceApplicationCmd = buildReferenceApplicationCmdFromApplicationDesc(applicationDesc);

        Application application = referenceApplicationUseCase.referenceApplication(referenceApplicationCmd);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{codeApp}")
            .buildAndExpand(application.getCodeApplication())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("applications/{codeApplication}")
    public ResponseEntity<Void> updateApplication(@NotNull @PathVariable("codeApplication") final String codeApplication,
                                                  @RequestBody ApplicationDesc applicationDesc) {

        ReferenceApplicationCmd referenceApplicationCmd = buildReferenceApplicationCmdFromApplicationDesc(applicationDesc);

        referenceApplicationUseCase.updateApplication(codeApplication, referenceApplicationCmd);

        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "applications/{codeApplication}", produces = {"application/json"})
    public ResponseEntity<ApplicationDesc> retrieveApplicationByCodeApplication(
        @NotNull @PathVariable("codeApplication") final String codeApplication) {

        final Application application = getApplicationQuery.getApplication(codeApplication);
        final ApplicationDesc applicationDesc = buildApplicationDescFromApplication(application);

        return new ResponseEntity<>(applicationDesc, HttpStatus.OK);
    }

    @GetMapping(value = "applications", produces = {"application/json"})
    public ResponseEntity<List<ApplicationIdent>> retrieveAllApplication() {

        List<ApplicationIdent> applicationsIdent = getApplicationQuery.getAllApplicationsIdent();

        return new ResponseEntity<>(applicationsIdent, HttpStatus.OK);
    }

    private ApplicationDesc buildApplicationDescFromApplication(Application application) {
        final ApplicationDesc applicationDesc = new ApplicationDesc();
        applicationDesc.setCodeApplication(application.getCodeApplication());
        applicationDesc.setShortDescription(application.getShortDescription());
        applicationDesc.setLongDescription(application.getLongDescription());
        applicationDesc.setUid(application.getResponsable().getUid());
        applicationDesc.setFirstName(application.getResponsable().getFirstName());
        applicationDesc.setLastName(application.getResponsable().getLastName());
        return applicationDesc;
    }

    private ReferenceApplicationCmd buildReferenceApplicationCmdFromApplicationDesc(@RequestBody ApplicationDesc applicationDesc) {
        return new ReferenceApplicationCmd(applicationDesc.getCodeApplication(),
            applicationDesc.getShortDescription(),
            applicationDesc.getLongDescription(),
            applicationDesc.getUid(),
            applicationDesc.getFirstName(),
            applicationDesc.getLastName(),
            applicationDesc.getContentNote(),
            applicationDesc.getContentVisibility());
    }

}
