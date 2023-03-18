package ru.practicum.viewStats.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.viewStats.ViewStatsDto;
import ru.practicum.viewStats.model.ViewStats;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewStatsMapper {

    public static ViewStats toViewStats(ViewStatsDto viewStatsDto) {
        return new ViewStats(
          viewStatsDto.getApp(),
          viewStatsDto.getUri(),
          viewStatsDto.getHits()
        );
    }

    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return new ViewStatsDto(
                viewStats.getApp(),
                viewStats.getUri(),
                viewStats.getHits()
        );
    }
}
