package ru.practicum.viewStats.repository;

import org.springframework.context.annotation.Lazy;

public class ViewStatsRepositoryImpl {
    private final ViewStatsRepository viewStatsRepository;

    public ViewStatsRepositoryImpl(@Lazy ViewStatsRepository viewStatsRepository){
        this.viewStatsRepository = viewStatsRepository;
    }
}
