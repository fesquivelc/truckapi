package com.test.truckapi.domain.exceptions;

public class TruckNotUnloadedException extends RuntimeException {
    public TruckNotUnloadedException(String message) {
        super(message);
    }
}
