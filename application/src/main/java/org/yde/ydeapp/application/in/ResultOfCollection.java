package org.yde.ydeapp.application.in;

public class ResultOfCollection {
    private int referenceCounter;
    private int updateCounter;
    private int ignoreCounter;
    private int noMoreUpdated;

    public ResultOfCollection(int ignored, int referenced, int updated, int nomoreupdated) {
        ignoreCounter = ignored;
        referenceCounter = referenced;
        updateCounter = updated;
        noMoreUpdated = nomoreupdated;
    }

    public ResultOfCollection() {
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

    public void referenceResult(StateCmdEnum stateCmdEnum) {
        switch (stateCmdEnum) {
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
