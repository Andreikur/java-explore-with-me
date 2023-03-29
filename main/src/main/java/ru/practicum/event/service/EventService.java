package ru.practicum.event.service;

import ru.practicum.event.dto.EventFulDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

public interface EventService {
    List<EventShortDto> getEventThisUser(Long userId, int from, int size);
    EventFulDto addEvent(Long userId, EventFulDto eventFulDto);
    EventFulDto getEventFullThisUser(Long userId, Long eventId);
    EventFulDto updateEventThisUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

}
