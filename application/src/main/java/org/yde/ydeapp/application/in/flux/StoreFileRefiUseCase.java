package org.yde.ydeapp.application.in.flux;


import org.springframework.validation.annotation.Validated;
import org.yde.ydeapp.application.common.SelfValidating;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.InputStream;

public interface StoreFileRefiUseCase {
    Long importRefiFlux(ImportRefiFluxCmd importRefiFluxCmd);

    @Validated
    class ImportRefiFluxCmd extends SelfValidating<ImportRefiFluxCmd> {

        @Size(min = 3, max = 100)
        private final String originalName;

        @NotNull
        private final InputStream inputStream;

        public ImportRefiFluxCmd(String originalName,
                                 InputStream inputStream) {
            this.originalName = originalName;
            this.inputStream = inputStream;
        }

        public String getOriginalName() {
            return originalName;
        }

        public InputStream getInputStream() {
            return inputStream;
        }
    }
}
