package org.yde.ydeapp.exposition.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.StoreFileRefiUseCase;
import org.yde.ydeapp.application.in.StoreFileRefiUseCase.ImportRefiFluxCmd;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/V1")
public class RefiRessource {

    @Autowired
    StoreFileRefiUseCase storeFileRefiUseCase;


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

}
