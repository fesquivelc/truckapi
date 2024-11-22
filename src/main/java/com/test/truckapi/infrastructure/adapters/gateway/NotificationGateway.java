package com.test.truckapi.infrastructure.adapters.gateway;

import com.test.truckapi.domain.entities.Notification;
import com.test.truckapi.domain.ports.output.NotificationPort;
import com.test.truckapi.infrastructure.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationGateway implements NotificationPort {
    private final KafkaConfig kafkaConfig;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Mono<Void> sendNotification(Notification<?> notification) {
        return Mono.fromRunnable(() -> {
            if (kafkaConfig.isEnabled()) {
                sendNotificationToKafka(notification);
            } else {
                sendFakeNotification(notification);
            }
        });
    }

    private void sendFakeNotification(Notification<?> notification) {
        log.info("emulate notification to kafka => {}", notification);
    }

    private void sendNotificationToKafka(Notification<?> notification) {
        kafkaTemplate.send(kafkaConfig.getTopic(), notification);
    }
}
