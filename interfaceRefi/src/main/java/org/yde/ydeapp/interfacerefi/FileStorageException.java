package org.yde.ydeapp.interfacerefi;

import org.yde.ydeapp.domain.out.BusinessException;

public class FileStorageException extends BusinessException {
    public FileStorageException(String message) {
        super(message);
    }
}