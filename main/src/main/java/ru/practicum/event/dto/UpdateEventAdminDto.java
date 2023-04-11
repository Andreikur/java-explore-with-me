package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.StateActionForAdmin;
import ru.practicum.location.model.Location;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventAdminDto {
    private String annotation;
    private Long category;
    private String description;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Pattern.DATE)
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private StateActionForAdmin stateAction;
    private String title;
}
