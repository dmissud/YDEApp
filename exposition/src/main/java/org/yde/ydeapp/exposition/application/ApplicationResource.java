package org.yde.ydeapp.exposition.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yde.ydeapp.application.in.application.ApplicationQuery;
import org.yde.ydeapp.application.in.application.ReferenceApplicationUseCase;
import org.yde.ydeapp.domain.application.Application;
import org.yde.ydeapp.domain.application.ApplicationIdent;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/V1")
public class ApplicationResource {

    @Autowired
    ReferenceApplicationUseCase referenceApplicationUseCase;

    @Autowired
    ApplicationQuery applicationQuery;

    @GetMapping(value = "applications/{codeApplication}", produces = {"application/json"})
    public ResponseEntity<Application> retrieveApplicationByCodeApplication(
        @NotNull
        @PathVariable("codeApplication") final String codeApplication) {

        final Application application = applicationQuery.getApplication(codeApplication);

        return new ResponseEntity<>(application, HttpStatus.OK);
    }

    @GetMapping(value = "applications", produces = {"application/json"})
    public ResponseEntity<List<ApplicationIdent>> retrieveAllApplication() {

        List<ApplicationIdent> applicationsIdent = applicationQuery.getAllApplicationsIdent();

        return new ResponseEntity<>(applicationsIdent, HttpStatus.OK);
    }

}
