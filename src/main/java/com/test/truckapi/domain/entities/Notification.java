package com.test.truckapi.domain.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification<T> {
    private String type;
    private T data;
}
