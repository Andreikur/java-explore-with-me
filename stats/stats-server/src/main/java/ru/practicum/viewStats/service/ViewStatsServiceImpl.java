package ru.practicum.viewStats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.endpointHit.repository.EndpointHitRepository;
import ru.practicum.viewStats.ViewStatsDto;
import ru.practicum.viewStats.mapper.ViewStatsMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ViewStatsServiceImpl implements ViewStatsService {
    private final EndpointHitRepository endpointHitRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (uris == null || uris.isEmpty()) {
            return Collections.emptyList();
        }
        if (unique) {
            return endpointHitRepository.getStatsUnique(start, end, uris).stream()
                    .map(ViewStatsMapper::toViewStatsDto)
                    .collect(Collectors.toList());
        } else {
            return endpointHitRepository.getStatsNotUnique(start, end, uris).stream()
                    .map(ViewStatsMapper::toViewStatsDto)
                    .collect(Collectors.toList());
        }
    }
}
