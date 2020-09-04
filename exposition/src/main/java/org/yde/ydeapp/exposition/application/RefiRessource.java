package org.yde.ydeapp.exposition.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.application.in.ResultOfCollection;
import org.yde.ydeapp.interfacerefi.StatTraitementRefiFile;
import org.yde.ydeapp.interfacerefi.StoreFileRefi;
import org.yde.ydeapp.interfacerefi.TransformerSourceToCmd;

@RestController
@RequestMapping("/api/V1")
public class RefiRessource {

    @Autowired
    StoreFileRefi storeFileRefi;

    @Autowired
    ReferenceApplicationUseCase referenceApplicationUseCase;


    @PostMapping("/uploadRefi")
    public ResponseEntity<StatRefiFileDto> uploadFile(@RequestParam("file") MultipartFile fileRefi) {

        storeFileRefi.storeRefiFile(fileRefi);
        TransformerSourceToCmd transformerSourceToCmd = (TransformerSourceToCmd) storeFileRefi.giveTransformerSourceToCmd();
        ResultOfCollection resultOfCollection = referenceApplicationUseCase.referenceOrUpdateCollectionOfApplication(transformerSourceToCmd);


        final StatTraitementRefiFile statTraitementRefiFile = transformerSourceToCmd.giveResult();
        StatRefiFileDto statRefiFileDto = new StatRefiFileDto(statTraitementRefiFile.getStatReadLineFile(),
            statTraitementRefiFile.getStatRejetedLinefile(),
            resultOfCollection.getReferenceCounter(),
            resultOfCollection.getUpdateCounter(),
            resultOfCollection.getIgnoreCounter(),
            resultOfCollection.getNoMoreUpdated());


        return new ResponseEntity<>(statRefiFileDto, HttpStatus.OK);

    }

}
