package ru.practicum.event.dto;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.enums.State;
import ru.practicum.location.model.Location;
import ru.practicum.user.dto.UserShortDto;

public class EventFulDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private  Integer confirmedRequest;  //потвержденные запросы
    private String createOn;            //Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String description;
    private String eventDate;           //(в формате "yyyy-MM-dd HH:mm:ss")
    private UserShortDto initiator;
    private Location location;
    private boolean paid;               //оплачиваемый?
    private Integer participanLimit;
    private String publishedOn;         //Нужна ли пре-модерация заявок на участие
    private boolean requestModeration = true;
    private State state;
    private String title;
    private Integer views;
}
