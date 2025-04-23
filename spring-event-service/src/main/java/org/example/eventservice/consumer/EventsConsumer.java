package org.example.eventservice.consumer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.eventservice.model.Event;
import org.example.eventservice.service.EventService;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventsConsumer {
    private final EventService eventService;
    private final ReactiveKafkaConsumerTemplate<String, Event> kafkaConsumerTemplate;

    @PostConstruct
    void poll() {
        kafkaConsumerTemplate.receive()
                .doOnNext(record ->
                        log.info("Received record from Kafka: {}", record))
                .flatMap(record -> Mono.just(record)
                        .map(ConsumerRecord::value)
                        .flatMap(eventService::save)
                        .flatMap(id -> record.receiverOffset().commit())
                )
                .onErrorContinue((throwable, msg) ->
                        log.error("Exception during handling msg from Kafka", throwable))
                .subscribe();
    }
}
