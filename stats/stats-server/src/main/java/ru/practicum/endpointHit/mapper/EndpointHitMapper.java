package ru.practicum.endpointHit.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.endpointHit.EndpointHitDto;
import ru.practicum.endpointHit.model.EndpointHit;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointHitMapper {

    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                .id(endpointHitDto.getId())
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .ip(endpointHitDto.getIp())
                .timestamp(endpointHitDto.getTimestamp())
                .build();
    }

    public static List<EndpointHit> toEndpointHit(Iterable<EndpointHitDto> endpointHitDtoList) {
        List<EndpointHit> endpointHitList = new ArrayList<>();
        for (EndpointHitDto endpointHitDto : endpointHitDtoList) {
            endpointHitList.add(toEndpointHit(endpointHitDto));
        }
        return endpointHitList;
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return EndpointHitDto.builder()
                .id(endpointHit.getId())
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }

    public static List<EndpointHitDto> toEndpointHitDto(Iterable<EndpointHit> endpointHitList) {
        List<EndpointHitDto> endpointHitDtoList = new ArrayList<>();
        for (EndpointHit endpointHit : endpointHitList) {
            endpointHitDtoList.add(toEndpointHitDto(endpointHit));
        }
        return endpointHitDtoList;
    }
}
