package com.test.truckapi.domain.exceptions;

public class LoadAlreadyUnloadedException extends RuntimeException {
    public LoadAlreadyUnloadedException(String message) {
        super(message);
    }
}
