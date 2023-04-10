package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventService;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEventsByCondition(@RequestParam(name = "users", required = false) List<Long> users,
                                                   @RequestParam(name = "states", required = false) List<String> states,
                                                   @RequestParam(name = "categories", required = false) List<Long> categories,
                                                   @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                                   @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                                   @RequestParam(name = "from", defaultValue = "0", required = false) Integer from,
                                                   @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {

        return eventService.getEventsByCondition(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@PathVariable Long eventId, @Valid @RequestBody EventShortDto eventShortDto) {
        return eventService.updateEvent(eventId, eventShortDto);
    }
}
