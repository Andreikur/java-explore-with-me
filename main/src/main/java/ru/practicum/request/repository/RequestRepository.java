package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.request.model.Request;

import java.util.List;

@RepositoryRestResource(path = "requestRepository")
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r " +
            "JOIN Event e ON r.event = e.id " +
            "WHERE r.event = ?2 AND e.initiator.id = ?1")
    List<Request> findAllByEventWithInitiator(Long userId, Long eventId);
}
