package org.yde.ydeapp.interfacerefi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import java.net.URI;
@RestController
@RequestMapping("/api")
public class RefiRessource {

    @Autowired
    StoreFileRefi storeFileRefi;

    @PostMapping("/uploadRefi")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile fileRefi) {

        String resultParse= storeFileRefi.storeRefiFile(fileRefi);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(fileRefi.getName())
                .buildAndExpand(resultParse)
                .toUri();


        return ResponseEntity.created(location).build();

    }
}
