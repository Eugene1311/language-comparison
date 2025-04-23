package org.example.eventservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.eventservice.entity.EventEntity;
import org.example.eventservice.mapper.EventMapper;
import org.example.eventservice.model.Event;
import org.example.eventservice.repository.ReactiveEventRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
class DomainEventService implements EventService {
    private final ReactiveEventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Mono<String> save(Event event) {
        return eventRepository.save(eventMapper.map(event))
                .doFirst(() -> log.info("Saving event: {}", event))
                .map(EventEntity::getId)
                .doOnNext(id -> log.info("Saved event with id: {}", id))
                .doOnError(throwable -> log.error("Error while saving event {} {}", event, throwable.getMessage()));
    }
}
