package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
    public List<EventFullDto> searchForEventsByParameters(@RequestParam(name = "text", required = false, defaultValue = "") String text,
                                                          @RequestParam(name = "categories", required = false) List<Long> categories,
                                                          @RequestParam(name = "paid", required = false) Boolean paid,
                                                          @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                                          @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                                          @RequestParam(name = "onlyAvailable", required = false) Boolean onlyAvailable,                    //события у которых не исчерпан лимит запросов на участие
                                                          @RequestParam(name = "sort", required = false) String sort,                              //Вариант сортировки: по дате события или по количеству просмотров (Available values : EVENT_DATE, VIEWS)
                                                          @Positive @RequestParam(defaultValue = "0", required = false) int from,
                                                          @PositiveOrZero @RequestParam(defaultValue = "10", required = false) int size,
                                                          HttpServletRequest request) {
        return eventService.searchForEventsByParameters(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable Long id, HttpServletRequest request) {
        return eventService.getEvent(id, request);
    }
}
