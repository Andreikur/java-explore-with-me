package ru.practicum.event.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.Pattern;
import ru.practicum.category.model.Category;
import ru.practicum.enums.State;
import ru.practicum.location.model.Location;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "events", schema = "public")
@Data
//@AllArgsConstructor
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation;
    @OneToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @Column(name = "confirmed_requests")
    private Long confirmedRequests;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @OneToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private User initiator;
    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    private Boolean paid;   //оплачиваемый?
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state;
    private String title;
    private Long views;

    //@Transient
    //private final String datePattern = Pattern.DATE;
    //@Transient
    //private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

    public Event(Long id, String annotation, Category category, Long confirmedRequests, LocalDateTime createdOn,
                 String description, LocalDateTime eventDate, User initiator, Location location, Boolean paid,
                 Integer participantLimit, LocalDateTime publishedOn, Boolean requestModeration, State eventState, String title, Long views) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        if (createdOn == null) {
            this.createdOn = LocalDateTime.now();
        } else {
            this.createdOn = createdOn;
        }
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        if (requestModeration == null) {
            this.requestModeration = true;
        } else {
            this.requestModeration = requestModeration;
        }
        if (eventState == null) {
            this.state = State.PENDING;
        } else {
            this.state = eventState;
        }
        this.title = title;
        this.views = views;
    }

}
