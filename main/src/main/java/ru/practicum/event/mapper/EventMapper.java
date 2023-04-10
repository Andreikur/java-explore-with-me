package ru.practicum.event.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.repository.CategoriesRepository;
import ru.practicum.event.dto.EventFulDto;
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

    public static Event toEvent(EventFulDto eventFulDto) {
        return new Event(
                eventFulDto.getId(),
                eventFulDto.getAnnotation(),
                CategoryMapper.toCategory(eventFulDto.getCategory()),
                eventFulDto.getConfirmedRequests(),
                LocalDateTime.parse(eventFulDto.getCreatedOn(), FORMATTER),
                eventFulDto.getDescription(),
                LocalDateTime.parse(eventFulDto.getEventDate(), FORMATTER),
                UserMapper.toUser(eventFulDto.getInitiator()),
                eventFulDto.getLocation(),
                eventFulDto.getPaid(),
                eventFulDto.getParticipantLimit(),
                LocalDateTime.parse(eventFulDto.getPublishedOn(), FORMATTER),
                eventFulDto.getRequestModeration(),
                eventFulDto.getState(),
                eventFulDto.getTitle(),
                eventFulDto.getViews()
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

    public static EventFulDto toEventFulDto(NewEventDto newEventDto) {
        return new EventFulDto(
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

    public static EventFulDto toEventFulDto(Event event) {
        return new EventFulDto(
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
                event.getPublishedOn().format(FORMATTER),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static List<Event> toEventFromEventFulDto(Iterable<EventFulDto> eventsFulDto) {
        List<Event> eventList = new ArrayList<>();
        for (EventFulDto eventFulDto : eventsFulDto) {
            eventList.add(toEvent(eventFulDto));
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

    public static List<EventFulDto> toEventFulDto(Iterable<Event> events) {
        List<EventFulDto> eventFulDtoList = new ArrayList<>();
        for (Event event : events) {
            eventFulDtoList.add(toEventFulDto(event));
        }
        return eventFulDtoList;
    }
}
