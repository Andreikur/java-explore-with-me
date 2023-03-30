package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFulDto;
import ru.practicum.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class PublicEventController {
    private final EventService eventService;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFulDto> searchForEventsByParameters(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) List<Long> categories,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false) String rangeStart,
                                                  @RequestParam(required = false) String rangeEnd,
                                                  @RequestParam(required = false) Boolean onlyAvailable,                    //события у которых не исчерпан лимит запросов на участие
                                                  @RequestParam(required = false) String sort,                              //Вариант сортировки: по дате события или по количеству просмотров (Available values : EVENT_DATE, VIEWS)
                                                  @Positive @RequestParam(defaultValue = "0", required = false) int from,
                                                  @PositiveOrZero @RequestParam(defaultValue = "10", required = false) int size) {
        return eventService.searchForEventsByParameters(text, categories, paid,  LocalDateTime.parse(rangeStart, FORMATTER),
                LocalDateTime.parse(rangeEnd, FORMATTER), onlyAvailable, sort, from, size);
    }
}
