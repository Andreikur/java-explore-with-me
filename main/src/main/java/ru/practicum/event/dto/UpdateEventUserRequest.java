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
    //private Long id;
    private String annotation;
    private Long category;
    //private  Long confirmedRequests;
    //private String createdOn;
    private String description;
    private LocalDateTime eventDate;           //(в формате "yyyy-MM-dd HH:mm:ss")
    //private UserShortDto initiator;
    private Location location;
    private Boolean paid;               //оплачиваемый?
    private Integer participantLimit;
    //private String publishedOn;
    private Boolean requestModeration;
    private StateActionForUser state;                //???????????????
    private String title;
    //private Integer views;
}
