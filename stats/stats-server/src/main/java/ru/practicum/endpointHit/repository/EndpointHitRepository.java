package ru.practicum.endpointHit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.endpointHit.model.EndpointHit;
import ru.practicum.viewStats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(path = "endpointHitRepository")
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT NEW ru.practicum.viewStats.model.ViewStats(e.app, e.uri, COUNT(DISTINCT e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp >= ?1 " +
            "AND e.timestamp <= ?2 " +
            "AND e.uri IN (?3) " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT (DISTINCT e.ip) DESC ")
    List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT NEW ru.practicum.viewStats.model.ViewStats(e.app, e.uri, COUNT(e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp >= ?1 " +
            "AND e.timestamp <= ?2 " +
            "AND e.uri IN (?3) " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT (e.ip) DESC ")
    List<ViewStats> getStatsNotUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}
