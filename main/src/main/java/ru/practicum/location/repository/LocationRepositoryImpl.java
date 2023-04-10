package ru.practicum.location.repository;

import org.springframework.context.annotation.Lazy;

public class LocationRepositoryImpl {
    public final LocationRepository locationRepository;

    public LocationRepositoryImpl(@Lazy LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
}
