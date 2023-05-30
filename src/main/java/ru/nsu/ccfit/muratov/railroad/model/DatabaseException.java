package ru.nsu.ccfit.muratov.railroad.model;

public class DatabaseException extends Exception {
    DatabaseException() {
        super();
    }

    DatabaseException(String message) {
        super(message);
    }

    DatabaseException(Throwable cause) {
        super(cause);
    }
}
