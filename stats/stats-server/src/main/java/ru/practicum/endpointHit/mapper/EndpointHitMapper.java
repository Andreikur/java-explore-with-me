package ru.practicum.endpointHit.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.endpointHit.EndpointHitDto;
import ru.practicum.endpointHit.model.EndpointHit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointHitMapper {

    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return new EndpointHit(
                endpointHitDto.getId(),
                endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                endpointHitDto.getTimestamp()
        );
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return new EndpointHitDto(
                endpointHit.getId(),
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp()
        );
    }
}
