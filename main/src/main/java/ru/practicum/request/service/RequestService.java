package ru.practicum.request.service;

import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestUpdateDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> getEventThisUserRequest(Long userId, Long eventId);
    List<RequestDto> updateStatusRequestsThisUser(Long userId, Long eventId, RequestUpdateDto requestUpdateDto);
}
