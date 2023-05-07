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
        return new EndpointHit(
                endpointHitDto.getId(),
                endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                endpointHitDto.getTimestamp()
        );
    }

    public static List<EndpointHit> toEndpointHit(Iterable<EndpointHitDto> endpointHitDtoList) {
        List<EndpointHit> endpointHitList = new ArrayList<>();
        for (EndpointHitDto endpointHitDto : endpointHitDtoList) {
            endpointHitList.add(toEndpointHit(endpointHitDto));
        }
        return endpointHitList;
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

    public static List<EndpointHitDto> toEndpointHitDto(Iterable<EndpointHit> endpointHitList) {
        List<EndpointHitDto> endpointHitDtoList = new ArrayList<>();
        for (EndpointHit endpointHit : endpointHitList) {
            endpointHitDtoList.add(toEndpointHitDto(endpointHit));
        }
        return endpointHitDtoList;
    }
}
