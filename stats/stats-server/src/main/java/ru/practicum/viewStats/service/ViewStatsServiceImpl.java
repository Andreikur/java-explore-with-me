package ru.practicum.viewStats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.viewStats.ViewStatsDto;
import ru.practicum.viewStats.repository.ViewStatsRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ViewStatsServiceImpl implements ViewStatsService {
    private final ViewStatsRepository viewStatsRepository;

    @Transactional
    @Override
    public List<ViewStatsDto> getStats(Date start, Date end, List<String> uris, boolean unique){

        List<ViewStatsDto> viewStatsDtos = new ArrayList<>();
        return viewStatsDtos;
    }

}
