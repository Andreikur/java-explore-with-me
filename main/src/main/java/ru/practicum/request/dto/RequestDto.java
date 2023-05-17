package ru.practicum.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.RequestStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class
RequestDto {
    private Long id;
    private String created;
    private Long event;
    private Long requester;
    private RequestStatus status;
}
