package ru.nsu.ccfit.muratov.railroad.factory.creator;

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
