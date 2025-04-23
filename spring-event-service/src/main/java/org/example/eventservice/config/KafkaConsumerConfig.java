package org.example.eventservice.config;

import org.example.eventservice.model.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.List;

@Configuration
public class KafkaConsumerConfig {
    @Value("${app.kafka.topic}")
    private String topic;

    @Bean
    public ReactiveKafkaConsumerTemplate<String, Event> kafkaConsumerTemplate(KafkaProperties kafkaProperties) {
        ReceiverOptions<String, Event> receiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());

        return new ReactiveKafkaConsumerTemplate<>(receiverOptions.subscription(List.of(topic)));
    }
}
