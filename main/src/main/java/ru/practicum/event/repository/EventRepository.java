package ru.practicum.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.enums.State;
import ru.practicum.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(path = "eventRepository")
//@Repository
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

    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE " +
            "(" +
            ":text IS NULL " +
            "OR LOWER(e.description) LIKE CONCAT('%', :text, '%') " +
            "OR LOWER(e.annotation) LIKE CONCAT('%', :text, '%')" +
            ")" +
            "AND (:states IS NULL OR e.state IN (:states)) " +
            "AND (:categories IS NULL OR e.category.id IN (:categories)) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND (CAST(:rangeStart AS date) IS NULL OR e.eventDate >= :rangeStart) " +
            "AND (CAST(:rangeEnd AS date) IS NULL OR e.eventDate <= :rangeEnd) " +
            "order by e.eventDate")
    List<Event> findByParamsOrderByDate(
            @Param("text") String text,
            @Param("states") List<State> states,
            @Param("categories") List<Long> categories,
            @Param("paid") Boolean paid,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable);

    List<Event> findAllByIdIn(List<Long> eventIds);

    Boolean existsByCategoryId(Long catId);
}
