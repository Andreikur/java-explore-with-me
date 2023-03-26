package ru.practicum.event.dto;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.enums.State;
import ru.practicum.location.model.Location;
import ru.practicum.user.dto.UserShortDto;

public class EventShortDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private String eventDate;           //(в формате "yyyy-MM-dd HH:mm:ss")
    private UserShortDto initiator;
    private boolean paid;               //оплачиваемый?
    private String title;
    private Integer views;
}
