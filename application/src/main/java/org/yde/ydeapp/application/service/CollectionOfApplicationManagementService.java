package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yde.ydeapp.application.in.*;

@Service
public class CollectionOfApplicationManagementService implements ReferenceCollectionOfApplicationUseCase {

    private final Logger log = LoggerFactory.getLogger(CollectionOfApplicationManagementService.class);

    @Autowired
    ReferenceApplicationUseCase referenceApplicationUseCase;

    @Override
    public ResultOfCollection referenceOrUpdateCollectionOfApplication(CollectionApplicationCmd collectionApplicationCmd) {
        ResultOfCollection resultOfCollection = new ResultOfCollection();
        for (ReferenceApplicationUseCase.ReferenceApplicationCmd referenceApplicationCmd : collectionApplicationCmd) {
            StateCmdEnum stateCmdEnum = referenceApplicationUseCase.referenceOrUpdateApplication(referenceApplicationCmd);
            switch (stateCmdEnum) {
                case IGNORE:
                    resultOfCollection.addIgnore();
                    break;
                case UPDATE:
                    resultOfCollection.addUpdate();
                    break;
                case REFERENCE:
                    resultOfCollection.addReference();
                    break;
                case NO_MORE_UPDATED:
                    resultOfCollection.addNoMoreUpdated();
                    break;
                default:
                    break;
            }
        }
        return resultOfCollection;
    }
}
