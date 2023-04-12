package ru.practicum.endpointHit.service;

import ru.practicum.endpointHit.EndpointHitDto;

public interface EndpointHitService {
    EndpointHitDto addEndpointHit(EndpointHitDto endpointHitDto);

    void addEndpointHitEvent(EndpointHitDto endpointHitDto);
}
