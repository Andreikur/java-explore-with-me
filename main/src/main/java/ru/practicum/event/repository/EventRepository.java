package ru.practicum.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;

import java.util.List;

//@RepositoryRestResource(path = "eventRepository")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT b FROM Event b " +
            "WHERE b.initiator.id = ?1")
    List<Event> findAllEventThisUserPage(Long userId, Pageable pageable);

    Boolean existsByCategoryId(Long catId);
}
