package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFulDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.service.EventService;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestUpdateDto;
import ru.practicum.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;
    private final RequestService requestService;

    /**
     * Получение событий добавленных текущим пользователем
     * @param userId
     * @param from
     * @param size
     * @return
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventThisUser(@PathVariable Long userId,
                                                @Positive @RequestParam(defaultValue = "0", required = false) int from,
                                                @PositiveOrZero @RequestParam(defaultValue = "10", required = false) int size) {
        return eventService.getEventThisUser(userId, from, size);
    };

    /**
     * Добавление нового события
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFulDto addEvent(@PathVariable Long userId,
                                @Valid @RequestBody EventFulDto eventFulDto) {
        return eventService.addEvent(userId, eventFulDto);
    }

    /**
     * Получение полной информации о событии добавленном текущим пользователем
     * @param userId
     * @param eventId
     * @return
     */
    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFulDto getEventFullThisUser(@PathVariable Long userId,
                                            @PathVariable Long eventId) {
        return eventService.getEventFullThisUser(userId, eventId);
    }

    /**
     * Изменение события добавленного текущим пользователем
     * @param userId
     * @param eventId
     * @param updateEventUserRequest
     * @return
     */
    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFulDto updateEventThisUser(@PathVariable Long userId,
                                           @PathVariable Long eventId,
                                           @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return eventService.updateEventThisUser(userId, eventId, updateEventUserRequest);
    }

    /**
     * Получение информации о запросах н аучастие в событии текущего пользователя
     * @param userId
     * @param eventId
     * @return
     */
    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestDto> getEventThisUserRequest(@PathVariable Long userId,
                                                     @PathVariable Long eventId) {
        return requestService.getEventThisUserRequest(userId, eventId);
    }

    @PatchMapping("/{eventId/requests}")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestDto> updateStatusRequestsThisUser(@PathVariable Long userId,
                                                         @PathVariable Long eventId,
                                                         @Valid @RequestBody RequestUpdateDto requestUpdateDto) {
        return requestService.updateStatusRequestsThisUser(userId, eventId, requestUpdateDto);
    }

}
