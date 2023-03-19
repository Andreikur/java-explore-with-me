package ru.practicum.endpointHit.repository;

import org.springframework.context.annotation.Lazy;

public class EndpointHitRepositoryImpl {
    private final EndpointHitRepository endpointHitRepository;

    public EndpointHitRepositoryImpl(@Lazy EndpointHitRepository endpointHitRepository) {
        this.endpointHitRepository = endpointHitRepository;
    }
}
