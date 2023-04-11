package ru.practicum.event.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.user.mapper.UserMapper;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventMapper {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Event toEvent(EventShortDto eventShortDto) {
        return new Event(
                eventShortDto.getId(),
                eventShortDto.getAnnotation(),
                CategoryMapper.toCategory(eventShortDto.getCategory()),
                null,
                null,
                null,
                LocalDateTime.parse(eventShortDto.getEventDate(), FORMATTER),
                UserMapper.toUser(eventShortDto.getInitiator()),
                null,
                eventShortDto.getPaid(),
                null,
                null,
                null,
                null,
                eventShortDto.getTitle(),
                eventShortDto.getViews()
        );
    }

    public static Event toEvent(EventFullDto eventFullDto) {
        return new Event(
                eventFullDto.getId(),
                eventFullDto.getAnnotation(),
                CategoryMapper.toCategory(eventFullDto.getCategory()),
                eventFullDto.getConfirmedRequests(),
                LocalDateTime.parse(eventFullDto.getCreatedOn(), FORMATTER),
                eventFullDto.getDescription(),
                LocalDateTime.parse(eventFullDto.getEventDate(), FORMATTER),
                UserMapper.toUser(eventFullDto.getInitiator()),
                eventFullDto.getLocation(),
                eventFullDto.getPaid(),
                eventFullDto.getParticipantLimit(),
                LocalDateTime.parse(eventFullDto.getPublishedOn(), FORMATTER),
                eventFullDto.getRequestModeration(),
                eventFullDto.getState(),
                eventFullDto.getTitle(),
                eventFullDto.getViews()
        );
    }

    public static Event toEvent(NewEventDto newEventDto) {
        return new Event(
                newEventDto.getId(),
                newEventDto.getAnnotation(),
                null,
                null,
                null,
                newEventDto.getDescription(),
                LocalDateTime.parse(newEventDto.getEventDate(),FORMATTER),
                null,
                //newEventDto.getLocation(),
                null,
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                null,
                newEventDto.getRequestModeration(),
                null,
                newEventDto.getTitle(),
                null
        );
    }

    public static EventFullDto toEventFulDto(NewEventDto newEventDto) {
        return new EventFullDto(
                null,
                newEventDto.getAnnotation(),
                null,
                null,
                null,
                newEventDto.getDescription(),
                newEventDto.getEventDate(),
                null,
                //newEventDto.getLocation(),
                null,
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                null,
                newEventDto.getRequestModeration(),
                null,
                newEventDto.getTitle(),
                null
        );
    }

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getEventDate().format(FORMATTER),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static EventFullDto toEventFulDto(Event event) {
        String dateTimeString;
        if (event.getPublishedOn() == null) {
            dateTimeString = null;
        } else {
            dateTimeString = event.getPublishedOn().format(FORMATTER);
        }
        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn().format(FORMATTER),
                event.getDescription(),
                event.getEventDate().format(FORMATTER),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                //event.getPublishedOn().format(FORMATTER),
                dateTimeString,
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static List<Event> toEventFromEventFulDto(Iterable<EventFullDto> eventsFulDto) {
        List<Event> eventList = new ArrayList<>();
        for (EventFullDto eventFullDto : eventsFulDto) {
            eventList.add(toEvent(eventFullDto));
        }
        return eventList;
    }

    public static List<Event> toEvent(Iterable<EventShortDto> eventShortDtos) {
        List<Event> eventList = new ArrayList<>();
        for (EventShortDto eventShortDto : eventShortDtos) {
            eventList.add(toEvent(eventShortDto));
        }
        return eventList;
    }

    public static List<EventShortDto> toEventShortDto(Iterable<Event> events) {
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Event userDto : events) {
            eventShortDtoList.add(toEventShortDto(userDto));
        }
        return eventShortDtoList;
    }

    public static List<EventFullDto> toEventFulDto(Iterable<Event> events) {
        List<EventFullDto> eventFulDtoList = new ArrayList<>();
        for (Event event : events) {
            eventFulDtoList.add(toEventFulDto(event));
        }
        return eventFulDtoList;
    }
}
