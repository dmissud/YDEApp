package org.yde.ydeapp.interfacerefi;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.application.in.ResultOfCollection;

import java.util.List;

@Component
public class YdeAppWriter implements ItemWriter<ReferenceApplicationUseCase.ReferenceApplicationCmd> {
    private final ReferenceApplicationUseCase referenceApplicationUseCase;
    private final ResultOfCollection resultOfCollection = new ResultOfCollection();

    @Autowired
    public YdeAppWriter(ReferenceApplicationUseCase referenceApplicationUseCase) {this.referenceApplicationUseCase = referenceApplicationUseCase;}

    @Override
    public void write(List<? extends ReferenceApplicationUseCase.ReferenceApplicationCmd> collectionApplicationCmd) {
        for (ReferenceApplicationUseCase.ReferenceApplicationCmd referenceApplicationCmd : collectionApplicationCmd) {
            resultOfCollection.referenceResult(referenceApplicationUseCase.referenceOrUpdateApplication(referenceApplicationCmd));
        }
    }
}
