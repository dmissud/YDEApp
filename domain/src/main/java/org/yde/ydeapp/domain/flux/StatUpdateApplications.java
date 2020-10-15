package org.yde.ydeapp.domain.flux;

public class StatUpdateApplications {
    private int referenceCounter;
    private int updateCounter;
    private int ignoreCounter;
    private int noMoreUpdated;

    public StatUpdateApplications(int referenceCounter, int updateCounter, int ignoreCounter, int noMoreUpdated) {
        this.referenceCounter = referenceCounter;
        this.updateCounter = updateCounter;
        this.ignoreCounter = ignoreCounter;
        this.noMoreUpdated = noMoreUpdated;
    }

    public StatUpdateApplications() {
        this.referenceCounter = 0;
        this.updateCounter = 0;
        this.ignoreCounter = 0;
        this.noMoreUpdated = 0;
    }


    public int getReferenceCounter() {
        return referenceCounter;
    }

    public int getUpdateCounter() {
        return updateCounter;
    }

    public int getIgnoreCounter() {
        return ignoreCounter;
    }

    public int getNoMoreUpdated() {
        return noMoreUpdated;
    }

    public void referenceResult(StateUpdateEnum stateUpdateEnum) {
        switch (stateUpdateEnum) {
            case IGNORE:
                ignoreCounter++;
                break;
            case UPDATE:
                updateCounter++;
                break;
            case REFERENCE:
                referenceCounter++;
                break;
            case NO_MORE_UPDATED:
                noMoreUpdated++;
                break;
            default:
                break;
        }
    }
}
