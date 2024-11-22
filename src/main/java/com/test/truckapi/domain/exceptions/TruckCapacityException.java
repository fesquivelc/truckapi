package com.test.truckapi.domain.exceptions;

public class TruckCapacityException extends RuntimeException {
    public TruckCapacityException(String message) {
        super(message);
    }
}
