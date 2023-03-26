package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventThisUser(@PathVariable Long userId,
                                                @Positive @RequestParam(defaultValue = "0", required = false) int from,
                                                @PositiveOrZero @RequestParam(defaultValue = "10", required = false) int size) {
        return eventService.getEventThisUser(userId, from, size);
    };
}
