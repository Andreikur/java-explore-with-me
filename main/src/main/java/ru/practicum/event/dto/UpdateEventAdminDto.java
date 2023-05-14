package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.StateActionForAdmin;
import ru.practicum.location.model.Location;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventAdminDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private StateActionForAdmin stateAction;
    private String title;
}
