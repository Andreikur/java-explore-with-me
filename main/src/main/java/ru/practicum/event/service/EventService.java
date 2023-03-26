package ru.practicum.event.service;

import ru.practicum.event.dto.EventShortDto;

import java.util.List;

public interface EventService {
    List<EventShortDto> getEventThisUser(Long userId, int from, int size);
    EventShortDto addEvent();
    EventShortDto getEventThisUserFull();
    EventShortDto updateEventThisUser();
    EventShortDto getEventThisUserRequest();
    EventShortDto updateEventThisUserRequest();

}
