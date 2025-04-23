package org.example.eventservice.mapper;

import org.example.eventservice.entity.EventEntity;
import org.example.eventservice.model.Event;
import org.mapstruct.Mapper;

@Mapper
public interface EventMapper {
    EventEntity map(Event event);
    Event map(EventEntity event);
}
