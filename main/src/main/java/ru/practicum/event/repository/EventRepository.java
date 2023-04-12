package ru.practicum.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.practicum.enums.State;
import ru.practicum.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

//@RepositoryRestResource(path = "eventRepository")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT b FROM Event b " +
            "WHERE b.initiator.id = ?1")
    List<Event> findAllEventThisUserPage(Long userId, Pageable pageable);

    @Query("SELECT e from Event e " +
            "where e.initiator.id = :users and " +
            "e.state = :state and " +
            "e.category.id = :categories and " +
            "e.publishedOn between :start and :end")
    List<Event> searchEvents(Long users, State state, Integer categories, LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Event> findAllByIdIn(List<Long> eventIds);

    Boolean existsByCategoryId(Long catId);
}
