package org.yde.ydeapp.domain.flux;

import java.time.Duration;
import java.time.LocalDateTime;

public class ImportJob {
    private String origninalName;
    private Long fluxId;
    private LocalDateTime start;
    private Duration duration;
    private int statReadLineFile;
    private int statRejetedLinefile;
    private int referenceCounter;
    private int updateCounter;
    private int ignoreCounter;
    private int noMoreUpdated;

    public ImportJob(String originalName, Long fluxId) {
        this.origninalName = originalName;
        this.fluxId = fluxId;
    }

    public String getOrigninalName() {
        return origninalName;
    }

    public Long getFluxId() {
        return fluxId;
    }

    public void setOrigninalName(String origninalName) {
        this.origninalName = origninalName;
    }

    public void setFluxId(Long fluxId) {
        this.fluxId = fluxId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
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
}
