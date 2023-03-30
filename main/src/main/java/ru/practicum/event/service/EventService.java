package ru.practicum.event.service;

import ru.practicum.event.dto.EventFulDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.request.dto.RequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getEventThisUser(Long userId, int from, int size);
    EventFulDto addEvent(Long userId, EventFulDto eventFulDto);
    EventFulDto getEventFullThisUser(Long userId, Long eventId);
    EventFulDto updateEventThisUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);
    List<EventFulDto> getEventsByCondition(List<Long> users, List<String> states, List<Long> categories,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);
    EventFulDto updateEvent(Long eventId, EventShortDto eventShortDto);
    List<EventFulDto> searchForEventsByParameters(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size);

}
