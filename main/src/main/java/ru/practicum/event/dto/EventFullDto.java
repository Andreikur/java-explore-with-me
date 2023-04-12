package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.enums.State;
import ru.practicum.location.model.Location;
import ru.practicum.user.dto.UserShortDto;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;  //потвержденные запросы
    private String createdOn;            //Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String description;
    private String eventDate;           //(в формате "yyyy-MM-dd HH:mm:ss")
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;               //оплачиваемый?
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;  //Нужна ли пре-модерация заявок на участие
    private State state;
    private String title;
    private Long views;
}
