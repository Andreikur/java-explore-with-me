package ru.practicum.endpointHit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.endpointHit.model.EndpointHit;
import ru.practicum.viewStats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(path = "endpoint_hit")
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {

    @Query("select new ru.practicum.viewStats.model.ViewStats(e.app, e.uri, count(distinct e.ip)) " +
            "from EndpointHit e " +
            "where e.timestamp >= ?1 " +
            "and e.timestamp <= ?2 " +
            "and e.uri IN (?3) " +
            "group by e.app, e.uri " +
            "order by count(distinct e.ip) desc")
    List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.viewStats.model.ViewStats(e.app, e.uri, count(e.ip)) " +
            "from EndpointHit e " +
            "where e.timestamp >= ?1 " +
            "and e.timestamp <= ?2 " +
            "and e.uri IN (?3) " +
            "group by e.app, e.uri " +
            "order by count(e.ip) desc")
    List<ViewStats> getStatsNotUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}
