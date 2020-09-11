package org.yde.ydeapp.interfacerefi;


import org.springframework.web.multipart.MultipartFile;
import org.yde.ydeapp.application.in.CollectionApplicationCmd;

public interface StoreFileRefi {
    StatusFile storeRefiFile(MultipartFile refiFile);
    MultipartFile retrieveRefiFile();
    CollectionApplicationCmd giveTransformerSourceToCmd();

}
