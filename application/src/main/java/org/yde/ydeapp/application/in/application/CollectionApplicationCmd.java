package org.yde.ydeapp.application.in.application;

import java.util.Collection;

public class CollectionApplicationCmd  {
    private final Collection<? extends ReferenceApplicationUseCase.ReferenceApplicationCmd> applicationCmdCollection;
    private final String importName;

    public CollectionApplicationCmd(Collection<? extends ReferenceApplicationUseCase.ReferenceApplicationCmd> applicationCmdCollection, String importName) {
        this.applicationCmdCollection = applicationCmdCollection;
        this.importName = importName;
    }

    public Collection<? extends ReferenceApplicationUseCase.ReferenceApplicationCmd> getApplicationCmdCollection() {
        return applicationCmdCollection;
    }

    public String getImportName() {
        return importName;
    }
}
