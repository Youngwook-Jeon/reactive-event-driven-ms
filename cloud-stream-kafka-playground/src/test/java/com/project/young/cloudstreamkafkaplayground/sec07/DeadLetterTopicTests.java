package com.project.young.cloudstreamkafkaplayground.sec07;

import com.project.young.cloudstreamkafkaplayground.AbstractIntegrationTests;
import com.project.young.cloudstreamkafkaplayground.sec05.dto.DigitalDelivery;
import com.project.young.cloudstreamkafkaplayground.sec05.dto.OrderEvent;
import com.project.young.cloudstreamkafkaplayground.sec05.dto.PhysicalDelivery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

@TestPropertySource(properties = {
        "sec=sec07",
        "spring.cloud.function.definition=processor;testProducer;charConsumer;dltConsumer",
        "spring.cloud.stream.bindings.testProducer-out-0.destination=input-topic",
        "spring.cloud.stream.bindings.processor-in-0.destination=input-topic",
        "spring.cloud.stream.bindings.processor-out-0.destination=output-topic",
        "spring.cloud.stream.bindings.charConsumer-in-0.destination=output-topic",
        "spring.cloud.stream.bindings.dltConsumer-in-0.destination=dlt-topic",
})
public class DeadLetterTopicTests extends AbstractIntegrationTests {

    private static final Logger log = LoggerFactory.getLogger(DeadLetterTopicTests.class);
    private static final Sinks.Many<String> reqSink = Sinks.many().unicast().onBackpressureBuffer();
    private static final Sinks.Many<Character> charSink = Sinks.many().unicast().onBackpressureBuffer();
    private static final Sinks.Many<String> dltSink = Sinks.many().unicast().onBackpressureBuffer();

    @Test
    public void charFinderTest() {
        reqSink.tryEmitNext("young");
        reqSink.tryEmitNext("luc");
        reqSink.tryEmitNext("michael");

        charSink.asFlux()
                .take(Duration.ofSeconds(1))
                .doOnNext(r -> log.info("char consumer received {}", r))
                .as(StepVerifier::create)
                .consumeNextWith(c -> Assertions.assertEquals('n', c))
                .consumeNextWith(c -> Assertions.assertEquals('h', c))
                .verifyComplete();

        dltSink.asFlux()
                .take(1)
                .doOnNext(r -> log.info("dlt consumer received {}", r))
                .as(StepVerifier::create)
                .consumeNextWith(r -> Assertions.assertEquals("luc", r))
                .verifyComplete();
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public Supplier<Flux<String>> testProducer(){
            return reqSink::asFlux;
        }

        @Bean
        public Consumer<Flux<Character>> charConsumer(){
            return f -> f.doOnNext(charSink::tryEmitNext).subscribe();
        }

        @Bean
        public Consumer<Flux<String>> dltConsumer(){
            return f -> f.doOnNext(dltSink::tryEmitNext).subscribe();
        }

    }
}
