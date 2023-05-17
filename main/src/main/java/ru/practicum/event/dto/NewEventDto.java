package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.location.dto.LocationDto;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewEventDto {
    private Long id;
    @NotNull
    private String annotation;
    @NotNull
    private Long category;
    @NotNull
    private String description;
    @NotNull
    private String eventDate;
    @NotNull
    private LocationDto location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    @NotNull
    private String title;
}
