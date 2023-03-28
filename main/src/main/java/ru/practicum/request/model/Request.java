package ru.practicum.request.model;

import ru.practicum.enums.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "request", schema = "public")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    private Long event;
    private Long requester;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
