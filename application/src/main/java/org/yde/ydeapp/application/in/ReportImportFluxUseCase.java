package org.yde.ydeapp.application.in;

import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;

import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;

public interface ReportImportFluxUseCase {
    void reportImportFlux(ReportImportFluxCmd reportImportFluxCmd);

    @Validated
    class ReportImportFluxCmd extends SelfValidating<ReportImportFluxCmd> {

        @Size(min = 3, max = 100)
        private final String fluxName;
        private final String status;
        private final String endStatus;
        private final String result;
        private final LocalDateTime start;
        private final Duration duration;
        private final int readCount;

        public ReportImportFluxCmd(String fluxName,
                                   String status,
                                   String endStatus,
                                   String result,
                                   LocalDateTime start,
                                   Duration duration,
                                   int readCount) {
            this.fluxName = fluxName;
            this.status = status;
            this.endStatus = endStatus;
            this.result = result;
            this.start = start;
            this.duration = duration;
            this.readCount = readCount;
        }

        public String getFluxName() {
            return fluxName;
        }

        public String getStatus() {
            return status;
        }

        public String getEndStatus() {
            return endStatus;
        }

        public String getResult() {
            return result;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public Duration getDuration() {
            return duration;
        }

        public int getReadCount() {
            return readCount;
        }
    }
}
