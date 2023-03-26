package ru.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.event.model.Event;

@RepositoryRestResource(path = "eventRepository")
public interface EventRepository extends JpaRepository<Event, Long> {
}
