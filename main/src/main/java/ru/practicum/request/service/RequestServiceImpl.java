package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestUpdateDto;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestServiceImpl implements RequestService {
    private final RequestService requestService;

    @Transactional(readOnly = true)
    @Override
    public List<RequestDto> getEventThisUserRequest(Long userId, Long eventId) {
        return requestService.getEventThisUserRequest(userId, eventId);
    }

    @Transactional
    @Override
    public List<RequestDto> updateStatusRequestsThisUser(Long userId, Long eventId, RequestUpdateDto requestUpdateDto) {

    }


}
