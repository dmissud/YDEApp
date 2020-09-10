package org.yde.ydeapp.exposition.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yde.ydeapp.application.in.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.application.in.ResultOfCollection;
import org.yde.ydeapp.interfacerefi.StatTraitementRefiFile;
import org.yde.ydeapp.interfacerefi.StoreFileRefi;
import org.yde.ydeapp.interfacerefi.TransformerSourceToCmd;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/V1")
public class RefiRessource {

    @Autowired
    StoreFileRefi storeFileRefi;

    @Autowired
    ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase;


    @PostMapping("/uploadRefi")
    public ResponseEntity<StatRefiFileDto> uploadFile(@RequestParam("file") MultipartFile fileRefi) {

        LocalDateTime start = LocalDateTime.now();

        storeFileRefi.storeRefiFile(fileRefi);
        TransformerSourceToCmd transformerSourceToCmd = (TransformerSourceToCmd) storeFileRefi.giveTransformerSourceToCmd();
        ResultOfCollection resultOfCollection = referenceCollectionOfApplicationUseCase.referenceOrUpdateCollectionOfApplication(transformerSourceToCmd);

        LocalDateTime stop = LocalDateTime.now();

        final StatTraitementRefiFile statTraitementRefiFile = transformerSourceToCmd.giveResult();
        StatRefiFileDto statRefiFileDto = new StatRefiFileDto(statTraitementRefiFile.getStatReadLineFile(),
            statTraitementRefiFile.getStatRejetedLinefile(),
            resultOfCollection.getReferenceCounter(),
            resultOfCollection.getUpdateCounter(),
            resultOfCollection.getIgnoreCounter(),
            resultOfCollection.getNoMoreUpdated(),
            Duration.between(start, stop).getSeconds());


        return new ResponseEntity<>(statRefiFileDto, HttpStatus.OK);

    }

}
