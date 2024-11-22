package com.test.truckapi.infrastructure.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Data
@NoArgsConstructor
public class KafkaConfig {
    private String servers;
    private String topic;
    private boolean enabled;
}
