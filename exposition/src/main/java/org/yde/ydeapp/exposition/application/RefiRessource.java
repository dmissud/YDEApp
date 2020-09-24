package org.yde.ydeapp.exposition.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.RefiImportQuery;
import org.yde.ydeapp.application.in.StoreFileRefiUseCase;
import org.yde.ydeapp.application.in.StoreFileRefiUseCase.ImportRefiFluxCmd;
import org.yde.ydeapp.domain.flux.ImportFlux;
import org.yde.ydeapp.domain.flux.ImportFluxIdent;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/V1")
public class RefiRessource {

    @Autowired
    StoreFileRefiUseCase storeFileRefiUseCase;

    @Autowired
    RefiImportQuery refiImportQuery;

    @PostMapping("/uploadBatchRefi")
    public ResponseEntity<Void> uploadFileWithBatch(@RequestParam("file") MultipartFile fileRefi) throws IOException {
        ImportRefiFluxCmd importRefiFluxCmd = new ImportRefiFluxCmd(fileRefi.getOriginalFilename(),
            fileRefi.getInputStream());
        Long importFluxId = storeFileRefiUseCase.importRefiFlux(importRefiFluxCmd);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{ifOfImportFlux}")
            .buildAndExpand(importFluxId)
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "uploadBatchRefi/{ifOfImportFlux}", produces = {"application/json"})
    public ResponseEntity<ImportFlux> retrieveApplicationByCodeApplication(
        @NotNull
        @PathVariable("ifOfImportFlux") final Long ifOfImportFlux) {

        final ImportFlux importFlux = refiImportQuery.getImportFlux(ifOfImportFlux);

        return new ResponseEntity<>(importFlux, HttpStatus.OK);
    }

    @GetMapping(value = "uploadBatchRefi", produces = {"application/json"})
    public ResponseEntity<List<ImportFluxIdent>> retrieveAllApplication() {

        List<ImportFluxIdent> importFluxIdent = refiImportQuery.getAllImportFlux();

        return new ResponseEntity<>(importFluxIdent, HttpStatus.OK);
    }

}
