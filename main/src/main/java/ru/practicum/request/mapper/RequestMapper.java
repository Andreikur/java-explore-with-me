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
        return new Request(
                requestDto.getId(),
                LocalDateTime.parse(requestDto.getCreated(), FORMATTER),
                requestDto.getEvent(),
                requestDto.getRequester(),
                requestDto.getStatus()
        );
    }

    public static RequestDto toRequestDto(Request request) {
        return new RequestDto(
                request.getId(),
                request.getCreated().format(FORMATTER),
                request.getEvent(),
                request.getRequester(),
                request.getStatus()
        );
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
