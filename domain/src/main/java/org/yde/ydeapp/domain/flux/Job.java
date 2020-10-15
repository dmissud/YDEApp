package org.yde.ydeapp.domain.flux;

import java.time.LocalDateTime;

public class Job {
    private final int readCount;
    private final String jobStatus;
    private final String existStatus;
    private final String resultDescription;
    private final LocalDateTime startDate;
    private final Long duration;

    public Job(int readCount,
               String jobStatus,
               String existStatus,
               String resultDescription,
               LocalDateTime startDate,
               Long duration) {
        this.readCount = readCount;
        this.jobStatus = jobStatus;
        this.existStatus = existStatus;
        this.resultDescription = resultDescription;
        this.startDate = startDate;
        this.duration = duration;
    }

    public Job() {
        this.readCount = 0;
        this.jobStatus = "none";
        this.existStatus = "none";
        this.resultDescription = "none";
        this.startDate = null;
        this.duration = 0L;
    }

    public int getReadCount() {
        return readCount;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public String getExistStatus() {
        return existStatus;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Long getDuration() {
        return duration;
    }

    public String getResultDescription() {
        return resultDescription;
    }
}
