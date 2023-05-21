package ru.nsu.ccfit.muratov.railroad.factory;

public class FactoryLoaderException extends Exception {
    public FactoryLoaderException() {
        super();
    }

    public FactoryLoaderException(String message) {
        super(message);
    }

    public FactoryLoaderException(Throwable cause) {
        super(cause);
    }

    public FactoryLoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
