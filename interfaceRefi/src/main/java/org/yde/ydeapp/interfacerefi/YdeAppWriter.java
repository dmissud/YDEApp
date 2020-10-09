package org.yde.ydeapp.interfacerefi;

import org.springframework.batch.item.ItemWriter;
import org.yde.ydeapp.application.in.application.CollectionApplicationCmd;
import org.yde.ydeapp.application.in.application.ReferenceApplicationUseCase.ReferenceApplicationCmd;
import org.yde.ydeapp.application.in.application.ReferenceCollectionOfApplicationUseCase;

import java.nio.file.Path;
import java.util.List;


public class YdeAppWriter implements ItemWriter<ReferenceApplicationCmd> {

    private final ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase;
    private String nameOfFile;

    public YdeAppWriter(ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase) {
        this.referenceCollectionOfApplicationUseCase = referenceCollectionOfApplicationUseCase;
    }

    @Override
    public void write(List<? extends ReferenceApplicationCmd> listOfApplicationCmd) {
        referenceCollectionOfApplicationUseCase.referenceOrUpdateCollectionOfApplication(new CollectionApplicationCmd(listOfApplicationCmd, this.nameOfFile));
    }

    public void workOn(Path pathRefiFileToImport) {
        this.nameOfFile = pathRefiFileToImport.getFileName().toString();
    }
}
