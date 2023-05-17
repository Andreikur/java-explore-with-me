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

    @Query("select new ru.practicum.viewStats.model.ViewStats(hit.app, hit.uri, COUNT(DISTINCT hit.ip)) " +
            "FROM EndpointHit hit " +
            "WHERE (hit.timestamp BETWEEN ?1 AND ?2) " +
            "AND hit.uri IN ?3 " +
            "GROUP BY hit.app, hit.uri " +
            "ORDER BY COUNT (DISTINCT hit.ip) DESC ")
    List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT NEW ru.practicum.viewStats.model.ViewStats(hit.app, hit.uri, COUNT(hit.ip)) " +
            "FROM EndpointHit hit " +
            "WHERE (hit.timestamp BETWEEN ?1 AND ?2) " +
            "AND hit.uri IN ?3 " +
            "GROUP BY hit.app, hit.uri " +
            "ORDER BY COUNT (hit.ip) DESC ")
    List<ViewStats> getStatsNotUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT NEW ru.practicum.viewStats.model.ViewStats(hit.app, hit.uri, COUNT(DISTINCT hit.ip)) " +
            "FROM EndpointHit hit " +
            "WHERE (hit.timestamp BETWEEN ?1 and ?2) " +
            "GROUP BY hit.app, hit.uri " +
            "ORDER BY COUNT(DISTINCT hit.ip) DESC ")
    List<ViewStats> getStatsWithoutUriUnique(LocalDateTime start, LocalDateTime end);

    @Query("SELECT NEW ru.practicum.viewStats.model.ViewStats(hit.app, hit.uri, COUNT (hit.ip)) " +
            "FROM EndpointHit hit " +
            "WHERE (hit.timestamp BETWEEN ?1 AND ?2) " +
            "GROUP BY hit.app, hit.uri " +
            "ORDER BY COUNT (hit.ip) DESC ")
    List<ViewStats> getStatsWithoutUriNotUnique(LocalDateTime start, LocalDateTime end);
}
