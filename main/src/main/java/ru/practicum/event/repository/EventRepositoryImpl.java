package ru.practicum.event.repository;

public class EventRepositoryImpl {
    private final EventRepository eventRepository;

    public EventRepositoryImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
