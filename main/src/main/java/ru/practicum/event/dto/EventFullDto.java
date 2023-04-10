package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.Pattern;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Pattern.DATE)
    private String createdOn;            //Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String description;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Pattern.DATE)
    private String eventDate;           //(в формате "yyyy-MM-dd HH:mm:ss")
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;               //оплачиваемый?
    private Integer participantLimit;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Pattern.DATE)
    private String publishedOn;
    private Boolean requestModeration;  //Нужна ли пре-модерация заявок на участие
    private State state;
    private String title;
    private Long views;
}
