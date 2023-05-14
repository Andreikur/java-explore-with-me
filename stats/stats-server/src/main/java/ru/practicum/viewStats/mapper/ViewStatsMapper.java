package ru.practicum.viewStats.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.viewStats.ViewStatsDto;
import ru.practicum.viewStats.model.ViewStats;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ViewStatsMapper {

    public static ViewStats toViewStats(ViewStatsDto viewStatsDto) {
        return ViewStats.builder()
                .app(viewStatsDto.getApp())
                .uri(viewStatsDto.getUri())
                .hits(viewStatsDto.getHits())
                .build();
    }

    public static List<ViewStats> toViewStats(Iterable<ViewStatsDto> viewStatsDtoList) {
        List<ViewStats> viewStatsList = new ArrayList<>();
        for (ViewStatsDto viewStatsDto : viewStatsDtoList) {
            viewStatsList.add(toViewStats(viewStatsDto));
        }
        return viewStatsList;
    }

    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return ViewStatsDto.builder()
                .app(viewStats.getApp())
                .uri(viewStats.getUri())
                .hits(viewStats.getHits())
                .build();
    }

    public static List<ViewStatsDto> toViewStatsDto(Iterable<ViewStats> viewStatsList) {
        List<ViewStatsDto> viewStatsDtoList = new ArrayList<>();
        for (ViewStats viewStats : viewStatsList) {
            viewStatsDtoList.add(toViewStatsDto(viewStats));
        }
        return viewStatsDtoList;
    }
}
