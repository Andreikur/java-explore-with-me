package ru.practicum.request.service;

import ru.practicum.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> getEventThisUserRequest(Long userId, Long eventId);
}
