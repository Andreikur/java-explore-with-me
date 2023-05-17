package ru.practicum.event.repository;

import org.springframework.context.annotation.Lazy;

public class EventRepositoryImpl {
    private final EventRepository eventRepository;

    public EventRepositoryImpl(@Lazy EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
