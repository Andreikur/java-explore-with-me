package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.enums.State;
import ru.practicum.enums.StateActionForUser;
import ru.practicum.location.model.Location;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;           //(в формате "yyyy-MM-dd HH:mm:ss")
    private Location location;
    private Boolean paid;               //оплачиваемый?
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionForUser stateAction;                //???????????????
    private String title;
}
