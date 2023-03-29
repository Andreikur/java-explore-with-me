package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.RequestStatus;
import ru.practicum.event.dto.RequestStatusUpdateResult;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ParticipantLimitException;
import ru.practicum.exception.RequestAlreadyConfirmedException;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestUpdateDto;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    @Override
    public List<RequestDto> getEventThisUserRequest(Long userId, Long eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        return RequestMapper.toRequestDto(requestRepository.findAllByEventWithInitiator(userId, eventId));
    }

    @Transactional
    @Override
    public RequestStatusUpdateResult updateStatusRequestsThisUser(Long userId, Long eventId, RequestUpdateDto requestUpdateDto) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Событие с таким Id не найдено")));
        RequestStatusUpdateResult result = new RequestStatusUpdateResult();
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            return result;
        }

        List<Request> requests = requestRepository.findAllByEventWithInitiator(userId, eventId);
        List<Request> requestsToUpdate = requests.stream().filter(x ->
                requestUpdateDto.getRequestIds().contains(x.getId())).collect(Collectors.toList());

        if (requestsToUpdate.stream().anyMatch(x -> x.getStatus().equals(RequestStatus.CONFIRMED) &&
                requestUpdateDto.getStatus().equals(RequestStatus.REJECTED))) {
            throw new RequestAlreadyConfirmedException("Запрос уже потвержден");
        }

        if (event.getConfirmedRequests() + requestsToUpdate.size() > event.getParticipantLimit() &&
                requestUpdateDto.getStatus().equals(RequestStatus.CONFIRMED)) {
            throw new ParticipantLimitException("Превышен лимит участников");
        }

        for (Request x : requestsToUpdate) {
            x.setStatus(RequestStatus.valueOf(requestUpdateDto.getStatus().toString()));
        }

        requestRepository.saveAll(requestsToUpdate);

        if (requestUpdateDto.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() + requestsToUpdate.size());
        }

        eventRepository.save(event);

        if (requestUpdateDto.getStatus().equals(RequestStatus.CONFIRMED)) {
            result.setConfirmedRequests(RequestMapper.toRequestDto(requestsToUpdate));
        }

        if (requestUpdateDto.getStatus().equals(RequestStatus.REJECTED)) {
            result.setRejectedRequests(RequestMapper.toRequestDto(requestsToUpdate));
        }

        return result;
    }

    @Transactional
    @Override
    public List<RequestDto> getRequest(Long userId, int from, int size) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        log.info("Пользователь с таким Id не найден");
        List<Request> requestList = new ArrayList<>();
        int page = from/size;
        PageRequest pageRequest = PageRequest.of(page, size);
        requestList.addAll(requestRepository.findAllRequestThisUserPage(userId, pageRequest));
        return RequestMapper.toRequestDto(requestList);
    }

    @Transactional
    @Override
    public RequestDto addRequest(Long userId, RequestDto requestDto) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        Request request = requestRepository.save(RequestMapper.toRequest(requestDto));
        return RequestMapper.toRequestDto(request);
    }

    @Transactional
    @Override
    public RequestDto cancellationRequestParticipateEvent(Long userId, Long requestId) {
        Request request = requestRepository.findByRequesterAndId(userId, requestId).orElseThrow(() ->
                new NotFoundException(String.format("Запрос с таким id не найден")));
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }
}
