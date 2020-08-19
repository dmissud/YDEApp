package org.yde.ydeapp.application.in;

public class ResultOfCollection {
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
    public void addReference(){
        referenceCounter ++;

    }
    public void addUpdate(){
        updateCounter ++;

    }
    public void addIgnore(){
        ignoreCounter ++;

    }

    public void addNoMoreUpdated() {
        noMoreUpdated++;
    }
}
