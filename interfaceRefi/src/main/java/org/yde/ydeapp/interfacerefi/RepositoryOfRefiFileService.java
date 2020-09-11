package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class RepositoryOfRefiFileService implements StoreFileRefi {
    private static final Logger log = LoggerFactory.getLogger(RepositoryOfRefiFileService.class);


    private String result = null;
    private MultipartFile refiFile = null;

    @Override
    public StatusFile storeRefiFile(MultipartFile refiFile) {

        this.refiFile = refiFile;

        if(refiFile.isEmpty()){
            return StatusFile.FILEKO;
        }
        return StatusFile.FILEOK;
    }

    @Override
    public MultipartFile retrieveRefiFile() {
        return refiFile;
    }

    public TransformerSourceToCmd giveTransformerSourceToCmd() {

        try {
            return new TransformerSourceToCmd
                    (new InputStreamReader(refiFile.getInputStream(), StandardCharsets.ISO_8859_1));

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return null;
    }
}


