package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.service.RequestService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {
    private final RequestService requestService;

    /**
     * получение информации о заявках текущего пользователя на участие в чужих событиях
     * @param userId
     * @param from
     * @param size
     * @return
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RequestDto> getRequest(@PathVariable Long userId,
                                       @Positive @RequestParam(defaultValue = "0", required = false) int from,
                                       @PositiveOrZero @RequestParam(defaultValue = "10", required = false) int size) {
        return requestService.getRequest(userId, from, size);
    }

    /**
     * добавление запроса от текущего пользователя на участие в событии
     * @param userId
     * @param eventId
     * @return
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private RequestDto addRequest(@PathVariable Long userId,
                                  @RequestParam(name = "eventId") Long eventId) {
        return requestService.addRequest(userId, eventId);
    }

    /**
     * отмена своего запроса на участие в событии
     * @param userId
     * @param requestId
     * @return
     */
    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public RequestDto cancellationRequestParticipateEvent(@PathVariable Long userId,
                                                          @PathVariable Long requestId) {
        return requestService.cancellationRequestParticipateEvent(userId, requestId);
    }
}
