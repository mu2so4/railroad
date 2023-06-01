package ru.nsu.ccfit.muratov.railroad.model.factory;

public class ProductCreatorException extends Exception {
    public ProductCreatorException() {
        super();
    }

    public ProductCreatorException(String message) {
        super(message);
    }

    public ProductCreatorException(Throwable cause) {
        super(cause);
    }

    public ProductCreatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
