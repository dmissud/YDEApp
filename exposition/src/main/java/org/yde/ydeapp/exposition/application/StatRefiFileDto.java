package org.yde.ydeapp.exposition.application;

public class StatRefiFileDto {
    private int statReadLineFile;
    private int statRejetedLinefile;
    private int referenceCounter;
    private int updateCounter;
    private int ignoreCounter;
    private int noMoreUpdated;
    private long duree;

    public StatRefiFileDto(int statReadLineFile, int statRejetedLinefile, int referenceCounter, int updateCounter, int ignoreCounter, int noMoreUpdated, long duree) {
        this.statReadLineFile = statReadLineFile;
        this.statRejetedLinefile = statRejetedLinefile;
        this.referenceCounter = referenceCounter;
        this.updateCounter = updateCounter;
        this.ignoreCounter = ignoreCounter;
        this.noMoreUpdated = noMoreUpdated;
        this.duree = duree;
    }

    public int getStatReadLineFile() {
        return statReadLineFile;
    }

    public void setStatReadLineFile(int statReadLineFile) {
        this.statReadLineFile = statReadLineFile;
    }

    public int getStatRejetedLinefile() {
        return statRejetedLinefile;
    }

    public void setStatRejetedLinefile(int statRejetedLinefile) {
        this.statRejetedLinefile = statRejetedLinefile;
    }

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

    public long getDuree() {
        return duree;
    }

    public void setDuree(long duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "StatRefiFileDto{" +
            "statReadLineFile=" + statReadLineFile +
            ", statRejetedLinefile=" + statRejetedLinefile +
            ", referenceCounter=" + referenceCounter +
            ", updateCounter=" + updateCounter +
            ", ignoreCounter=" + ignoreCounter +
            ", noMoreUpdated=" + noMoreUpdated +
            ", duree=" + duree +
            '}';
    }
}
