package org.example.eventservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.eventservice.model.Payload;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "events")
@Getter
@Setter
@ToString
public class EventEntity {
    @Id
    private String id;
    private Payload payload;
}
