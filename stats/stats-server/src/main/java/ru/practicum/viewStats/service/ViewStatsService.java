package ru.practicum.viewStats.service;

import ru.practicum.viewStats.ViewStatsDto;

import java.util.Date;
import java.util.List;

public interface ViewStatsService {
    List<ViewStatsDto> getStats(Date start, Date end, List<String> uris, boolean unique);
}
