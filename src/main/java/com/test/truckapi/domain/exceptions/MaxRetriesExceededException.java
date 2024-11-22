package com.test.truckapi.domain.exceptions;

public class MaxRetriesExceededException extends RuntimeException {
    public MaxRetriesExceededException() {
        super();
    }

    public MaxRetriesExceededException(String message) {
        super(message);
    }
}
