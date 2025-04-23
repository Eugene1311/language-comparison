package org.example.eventservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record Event(
        @JsonProperty
        UUID id,
        @JsonProperty
        Payload payload
) {
}
