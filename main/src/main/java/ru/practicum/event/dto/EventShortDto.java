package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventShortDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private String eventDate;           //(в формате "yyyy-MM-dd HH:mm:ss")
    private UserShortDto initiator;
    private Boolean paid;               //оплачиваемый?
    private String title;
    private Long views;
}
