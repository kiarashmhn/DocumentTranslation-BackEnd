package com.document.documentTranslator.exception;


import com.document.documentTranslator.enums.ErrorMessage;

public class DomainException extends Exception {

    private static final long serialVersionUID = 1L;

    private final ErrorMessage errorMessage;

    public DomainException() {
        super();
        errorMessage = null;
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
        errorMessage = null;
    }

    public DomainException(String message) {
        super(message);
        errorMessage = null;
    }

    public DomainException(Throwable cause) {
        super(cause);
        errorMessage = null;
    }

    public DomainException(String message, ErrorMessage errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {

        return errorMessage;
    }

    public DomainException(ErrorMessage errorMessage) {
        super(errorMessage.getFarsiMessage());
        this.errorMessage = errorMessage;
    }

}