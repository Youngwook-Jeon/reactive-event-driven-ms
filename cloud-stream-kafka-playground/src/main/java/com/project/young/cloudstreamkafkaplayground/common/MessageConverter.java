package com.project.young.cloudstreamkafkaplayground.common;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import reactor.kafka.receiver.ReceiverOffset;

public class MessageConverter {

    public static <T> Record<T> toRecord(Message<T> message) {
        var payload = message.getPayload();
        var key = message.getHeaders().get(KafkaHeaders.KEY, String.class);
        var ack = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, ReceiverOffset.class);
        return new Record<>(key, payload, ack);
    }
}
