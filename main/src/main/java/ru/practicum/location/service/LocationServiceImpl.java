package ru.practicum.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.model.Location;
import ru.practicum.location.repository.LocationRepository;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Transactional
    @Override
    public Location addLocationForEvent(LocationDto locationDto) {
        return locationRepository.save(LocationMapper.toLocation(locationDto));
    }
}
