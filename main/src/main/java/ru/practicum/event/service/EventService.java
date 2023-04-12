package ru.practicum.event.service;

import ru.practicum.enums.State;
import ru.practicum.event.dto.*;
import ru.practicum.location.model.Location;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    List<EventShortDto> getEventThisUser(Long userId, int from, int size);

    EventFullDto addEvent(Long userId, NewEventDto newEventDto, Location location);

    EventFullDto getEventFullThisUser(Long userId, Long eventId);

    EventFullDto updateEventThisUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    List<EventFullDto> getEventsByCondition(List<Long> users, State states, List<Long> categories,
                                            String rangeStart, String rangeEnd, int from, int size);

    EventFullDto updateEvent(Long eventId, UpdateEventAdminDto updateEventAdminDto);

    List<EventFullDto> searchForEventsByParameters(String text, List<Long> categories, Boolean paid, String rangeStart,
                                                   String rangeEnd, Boolean onlyAvailable, String sort,
                                                   int from, int size, HttpServletRequest request);

    EventFullDto getEvent(Long eventId);

}
