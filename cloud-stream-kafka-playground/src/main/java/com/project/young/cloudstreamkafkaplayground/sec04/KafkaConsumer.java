package com.project.young.cloudstreamkafkaplayground.sec04;

import com.project.young.cloudstreamkafkaplayground.common.MessageConverter;
import com.project.young.cloudstreamkafkaplayground.common.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @Bean
    public Consumer<Flux<Message<String>>> consumer() {
        return flux -> flux
                .map(MessageConverter::toRecord)
                .doOnNext(this::printMessageDetails)
                .subscribe();
    }

    private void printMessageDetails(Record<String> rec) {
        log.info("payload: {}", rec.message());
        log.info("headers: {}", rec.key());
        rec.acknowledgement().acknowledge();
    }
}
