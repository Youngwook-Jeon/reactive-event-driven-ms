package com.project.young.cloudstreamkafkaplayground.common;

import reactor.kafka.receiver.ReceiverOffset;

public record Record<T>(
        String key,
        T message,
        ReceiverOffset acknowledgement
) {
}
