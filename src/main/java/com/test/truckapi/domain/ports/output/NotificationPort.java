package com.test.truckapi.domain.ports.output;

import com.test.truckapi.domain.entities.Notification;
import reactor.core.publisher.Mono;

public interface NotificationPort {
    Mono<Void> sendNotification(Notification<?> notification);
}
