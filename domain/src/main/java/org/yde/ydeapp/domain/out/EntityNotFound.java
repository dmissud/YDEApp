package org.yde.ydeapp.domain.out;

public class EntityNotFound extends BusinessException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public EntityNotFound(String message) {
        super(message, "ENTITY_NOT_FOUND");
    }
}
