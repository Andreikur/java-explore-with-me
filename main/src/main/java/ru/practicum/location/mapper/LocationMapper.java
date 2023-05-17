package ru.practicum.location.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.location.model.Location;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationMapper {
    public static Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

    public static LocationDto toLocation(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
