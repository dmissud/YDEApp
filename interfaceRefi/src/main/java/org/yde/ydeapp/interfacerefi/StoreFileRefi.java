package org.yde.ydeapp.interfacerefi;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface StoreFileRefi {
    String storeRefiFile(MultipartFile refiFile);
    String storeRefiLight(MultipartFile refiFile);


}
