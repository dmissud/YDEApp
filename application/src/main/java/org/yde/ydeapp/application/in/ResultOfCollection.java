package org.yde.ydeapp.application.in;

public class ResultOfCollection {
    private int referenceCounter;
    private int updateCounter;
    private int ignoreCounter;

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

    public void AddReference (){
        referenceCounter ++;

    }
    public void AddUpdate (){
        updateCounter ++;

    }
    public void AddIgnore (){
        ignoreCounter ++;

    }

}
