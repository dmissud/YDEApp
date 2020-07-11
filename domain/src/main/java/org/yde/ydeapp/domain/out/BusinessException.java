package org.yde.ydeapp.domain.out;

public class BusinessException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BusinessException(String message) {
        super(message);
        this.code = "UNKNOW_BUSINESS_ERROR";
    }

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    private final String code;

    public String getCode() { return code; }

}
