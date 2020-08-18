package org.yde.ydeapp.interfacerefi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.CollectionApplicationCmd;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.application.in.ResultOfCollection;


import java.net.URI;

@RestController
@RequestMapping("/api")
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
                resultOfCollection.getIgnoreCounter());


        return new ResponseEntity<>(statRefiFileDto, HttpStatus.OK);

    }

}
