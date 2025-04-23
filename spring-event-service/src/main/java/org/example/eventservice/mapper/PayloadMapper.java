package org.example.eventservice.mapper;

import org.example.eventservice.entity.PayloadEntity;
import org.example.eventservice.model.Payload;
import org.mapstruct.Mapper;

@Mapper
public interface PayloadMapper {
    PayloadEntity map(Payload payload);
    Payload map(PayloadEntity payload);
}
