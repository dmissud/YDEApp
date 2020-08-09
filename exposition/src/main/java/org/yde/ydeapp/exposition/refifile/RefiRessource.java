package org.yde.ydeapp.exposition.refifile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.ReferenceParseRefiUseCase;
import org.yde.ydeapp.application.in.StoreFileRefiUseCase;


import java.io.File;
import java.net.URI;
@RestController
@RequestMapping("/api")
public class RefiRessource {
    @Autowired
    ReferenceParseRefiUseCase referenceParseRefiUseCase;

    @Autowired
    StoreFileRefiUseCase storeFileRefiUseCase;

    @PostMapping("/uploadRefi")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile fileRefi) {

        String sourceRefi = storeFileRefiUseCase.storeRefiFile(fileRefi);
        String resultParse = referenceParseRefiUseCase.referenceParseRefi(sourceRefi);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(fileRefi.getName())
                .buildAndExpand(resultParse)
                .toUri();


        return ResponseEntity.created(location).build();

    }
}
