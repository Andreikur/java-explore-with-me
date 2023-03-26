package ru.practicum.event.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import ru.practicum.event.dto.EventFulDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Mapper
public interface EventMapper {

    Event toEvent(EventShortDto eventShortDto);
    Event toEvent(EventFulDto eventFulDto);
    EventShortDto  toEventShortDto(Event event);
    EventFulDto toEventFulDto(Event event);

/*    public static Event toEvent(EventShortDto eventShortDto) {
        return new Event(
                eventShortDto.getId(),
                eventShortDto.getAnnotation(),
                eventShortDto.getCategory(),
                null,
                eventShortDto.getEventDate(),
                null,
                eventShortDto.getInitiator(),
                eventShortDto.isPaid(),
                null,
                null
        );
    }*/
}
