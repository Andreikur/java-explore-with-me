package ru.practicum.viewStats.service;

import ru.practicum.viewStats.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ViewStatsService {
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
