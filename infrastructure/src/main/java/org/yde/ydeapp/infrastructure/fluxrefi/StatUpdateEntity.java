package org.yde.ydeapp.infrastructure.fluxrefi;

import javax.persistence.Embeddable;

@Embeddable
public class StatUpdateEntity {
    private int referenceCounter;
    private int updateCounter;
    private int ignoreCounter;
    private int noMoreUpdated;

    public int getReferenceCounter() {
        return referenceCounter;
    }

    public void setReferenceCounter(int referenceCounter) {
        this.referenceCounter = referenceCounter;
    }

    public int getUpdateCounter() {
        return updateCounter;
    }

    public void setUpdateCounter(int updateCounter) {
        this.updateCounter = updateCounter;
    }

    public int getIgnoreCounter() {
        return ignoreCounter;
    }

    public void setIgnoreCounter(int ignoreCounter) {
        this.ignoreCounter = ignoreCounter;
    }

    public int getNoMoreUpdated() {
        return noMoreUpdated;
    }

    public void setNoMoreUpdated(int noMoreUpdated) {
        this.noMoreUpdated = noMoreUpdated;
    }
}
