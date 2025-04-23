package org.example.eventservice.service;

import org.example.eventservice.model.Event;
import reactor.core.publisher.Mono;

public interface EventService {
    Mono<String> save(Event event);
}
