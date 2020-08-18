package org.yde.ydeapp.domain.out;

public class EntityIncorrect extends BusinessException {
        /**
         * Constructs a new runtime exception with {@code null} as its
         * detail message.  The cause is not initialized, and may subsequently be
         * initialized by a call to {@link #initCause}.
         */
    public EntityIncorrect(String message) {
            super(message, "ENTITY_INCORRECT");
        }
}
