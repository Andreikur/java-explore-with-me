package ru.practicum.viewStats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.viewStats.model.ViewStats;

@RepositoryRestResource(path = "view_stats")
public interface ViewStatsRepository extends JpaRepository<ViewStats, String> {
}
