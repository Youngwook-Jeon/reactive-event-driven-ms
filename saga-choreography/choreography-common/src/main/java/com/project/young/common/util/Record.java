package com.project.young.common.util;

import reactor.kafka.receiver.ReceiverOffset;

public record Record<T>(
        String key,
        T message,
        ReceiverOffset acknowledgement
) {
}
