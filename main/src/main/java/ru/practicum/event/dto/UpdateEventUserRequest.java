package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.StateActionForUser;
import ru.practicum.location.model.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventUserRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;           //(в формате "yyyy-MM-dd HH:mm:ss")
    private Location location;
    private Boolean paid;               //оплачиваемый?
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionForUser stateAction;
    private String title;
}
