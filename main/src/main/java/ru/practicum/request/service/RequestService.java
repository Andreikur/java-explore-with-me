package ru.practicum.request.service;

import ru.practicum.event.dto.RequestStatusUpdateResult;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestUpdateDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> getEventThisUserRequest(Long userId, Long eventId);

    RequestStatusUpdateResult updateStatusRequestsThisUser(Long userId, Long eventId, RequestUpdateDto requestUpdateDto);

    List<RequestDto> getRequest(Long userId, int from, int size);

    RequestDto addRequest(Long userId, Long eventId);

    RequestDto cancellationRequestParticipateEvent(Long userId, Long requestId);
}
