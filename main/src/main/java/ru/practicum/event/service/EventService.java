package ru.practicum.event.service;

import ru.practicum.event.dto.EventShortDto;

public interface EventService {
    EventShortDto getEventThisUser();
    EventShortDto addEvent();
    EventShortDto getEventThisUserFull();
    EventShortDto updateEventThisUser();
    EventShortDto getEventThisUserRequest();
    EventShortDto updateEventThisUserRequest();

}
