package ru.practicum.request.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.model.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestMapper {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Request toRequest(RequestDto requestDto) {
        return Request.builder()
                .id(requestDto.getId())
                .created(LocalDateTime.parse(requestDto.getCreated(), FORMATTER))
                .event(requestDto.getEvent())
                .requester(requestDto.getRequester())
                .status(requestDto.getStatus())
                .build();
    }

    public static RequestDto toRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .created(request.getCreated().format(FORMATTER))
                .event(request.getEvent())
                .requester(request.getRequester())
                .status(request.getStatus())
                .build();
    }

    public static List<Request> toRequest(Iterable<RequestDto> requestDtos) {
        List<Request> requestList = new ArrayList<>();
        for (RequestDto requestDto : requestDtos) {
            requestList.add(toRequest(requestDto));
        }
        return requestList;
    }

    public static List<RequestDto> toRequestDto(Iterable<Request> requests) {
        List<RequestDto> requestDtoList = new ArrayList<>();
        for (Request request : requests) {
            requestDtoList.add(toRequestDto(request));
        }
        return requestDtoList;
    }
}
