package org.yde.ydeapp.exposition.flux;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.flux.RefiImportQuery;
import org.yde.ydeapp.application.in.flux.StoreFileRefiUseCase;
import org.yde.ydeapp.application.in.flux.StoreFileRefiUseCase.ImportRefiFluxCmd;
import org.yde.ydeapp.domain.flux.ImportFlux;
import org.yde.ydeapp.domain.flux.ImportFluxIdent;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/V1")
@CrossOrigin()
public class RefiRessource {

    @Autowired
    StoreFileRefiUseCase storeFileRefiUseCase;

    @Autowired
    RefiImportQuery refiImportQuery;

    @Secured("ROLE_ADMIN")
    @PostMapping("/uploadBatchRefi")
    public ResponseEntity<Void> uploadFileWithBatch(@RequestParam("fluxRefi") MultipartFile fileRefi) throws IOException {
        ImportRefiFluxCmd importRefiFluxCmd = new ImportRefiFluxCmd(fileRefi.getOriginalFilename(),
            fileRefi.getInputStream());
        Long importFluxId = storeFileRefiUseCase.importRefiFlux(importRefiFluxCmd);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{ifOfImportFlux}")
            .buildAndExpand(importFluxId)
            .toUri();
        return ResponseEntity.created(location).header("Access-Control-Expose-Headers", "Location").build();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/uploadBatchRefi/{ifOfImportFlux}", produces = {"application/json"})
    public ResponseEntity<ImportFlux> retrieveImportFluxById(
        @NotNull
        @PathVariable("ifOfImportFlux") final Long ifOfImportFlux) {

        final ImportFlux importFlux = refiImportQuery.getImportFlux(ifOfImportFlux);

        return new ResponseEntity<>(importFlux, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/uploadBatchRefi/{ifOfImportFlux}", produces = {"application/json"})
    public ResponseEntity<Void> deleteImportFluxById(
        @NotNull
        @PathVariable("ifOfImportFlux") final Long ifOfImportFlux) {

        refiImportQuery.deleteImportFlux(ifOfImportFlux);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/uploadBatchRefi", produces = {"application/json"})
    public ResponseEntity<List<ImportFluxIdent>> retrieveAllApplication() {

        List<ImportFluxIdent> importFluxIdent = refiImportQuery.getAllImportFlux();

        return new ResponseEntity<>(importFluxIdent, HttpStatus.OK);
    }

}
