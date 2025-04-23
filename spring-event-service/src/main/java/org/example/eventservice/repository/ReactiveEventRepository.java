package org.example.eventservice.repository;

import org.example.eventservice.entity.EventEntity;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveEventRepository extends ReactiveElasticsearchRepository<EventEntity, String> {
}
