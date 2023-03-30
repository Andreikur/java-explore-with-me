package ru.practicum.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.RequestStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateDto {
    private List<Long> requestIds;
    private RequestStatus status;
}