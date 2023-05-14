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
        return Event.builder()
                .id(eventShortDto.getId())
                .annotation(eventShortDto.getAnnotation())
                .category(CategoryMapper.toCategory(eventShortDto.getCategory()))
                .eventDate(LocalDateTime.parse(eventShortDto.getEventDate(), FORMATTER))
                .initiator(UserMapper.toUser(eventShortDto.getInitiator()))
                .paid(eventShortDto.getPaid())
                .title(eventShortDto.getTitle())
                .views(eventShortDto.getViews())
                .build();
    }

    public static Event toEvent(EventFullDto eventFullDto) {
        return Event.builder()
                .id(eventFullDto.getId())
                .annotation(eventFullDto.getAnnotation())
                .category(CategoryMapper.toCategory(eventFullDto.getCategory()))
                .confirmedRequests(eventFullDto.getConfirmedRequests())
                .createdOn(LocalDateTime.parse(eventFullDto.getCreatedOn(), FORMATTER))
                .description(eventFullDto.getDescription())
                .eventDate(LocalDateTime.parse(eventFullDto.getEventDate(), FORMATTER))
                .initiator(UserMapper.toUser(eventFullDto.getInitiator()))
                .location(eventFullDto.getLocation())
                .paid(eventFullDto.getPaid())
                .participantLimit(eventFullDto.getParticipantLimit())
                .publishedOn(LocalDateTime.parse(eventFullDto.getPublishedOn(), FORMATTER))
                .requestModeration(eventFullDto.getRequestModeration())
                .state(eventFullDto.getState())
                .title(eventFullDto.getTitle())
                .views(eventFullDto.getViews())
                .build();
    }

    public static Event toEvent(NewEventDto newEventDto) {
        return Event.builder()
                .id(newEventDto.getId())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate(),FORMATTER))
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .build();
    }

    public static EventFullDto toEventFulDto(NewEventDto newEventDto) {
        return EventFullDto.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .eventDate(event.getEventDate().format(FORMATTER))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventFullDto toEventFulDto(Event event) {
        String dateTimeString;
        if (event.getPublishedOn() == null) {
            dateTimeString = null;
        } else {
            dateTimeString = event.getPublishedOn().format(FORMATTER);
        }
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().format(FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(FORMATTER))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(dateTimeString)
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
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
