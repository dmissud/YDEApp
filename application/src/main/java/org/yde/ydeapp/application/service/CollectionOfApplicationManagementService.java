package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yde.ydeapp.application.in.CollectionApplicationCmd;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;
import org.yde.ydeapp.application.in.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.application.in.ResultOfCollection;

@Service
public class CollectionOfApplicationManagementService implements ReferenceCollectionOfApplicationUseCase {

    private final Logger log = LoggerFactory.getLogger(CollectionOfApplicationManagementService.class);

    @Autowired
    ReferenceApplicationUseCase referenceApplicationUseCase;

    @Override
    public ResultOfCollection referenceOrUpdateCollectionOfApplication(CollectionApplicationCmd collectionApplicationCmd) {
        ResultOfCollection resultOfCollection = new ResultOfCollection();
        for (ReferenceApplicationUseCase.ReferenceApplicationCmd referenceApplicationCmd : collectionApplicationCmd) {
            resultOfCollection.referenceResult(referenceApplicationUseCase.referenceOrUpdateApplication(referenceApplicationCmd));
        }
        return resultOfCollection;
    }
}
