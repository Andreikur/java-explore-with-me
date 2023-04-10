package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.Pattern;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.location.model.Location;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    private Long id;
    @NotNull
    private String annotation;
    @NotNull
    private Long category;
    @NotNull
    private String description;
    @NotNull
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Pattern.DATE)
    //private LocalDateTime eventDate;
    private String eventDate;
    @NotNull
    private LocationDto location;    //?? DTO
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    @NotNull
    private String title;
}
