package org.yde.ydeapp.infrastructure.fluxrefi;

import org.yde.ydeapp.domain.out.BusinessException;

public class FileStorageException extends BusinessException {
    public FileStorageException(String message) {
        super(message);
    }
}