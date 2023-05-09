package ru.practicum.viewStats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.endpointHit.repository.EndpointHitRepository;
import ru.practicum.viewStats.ViewStatsDto;
import ru.practicum.viewStats.mapper.ViewStatsMapper;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ViewStatsServiceImpl implements ViewStatsService {
    private final EndpointHitRepository endpointHitRepository;

    //@Transactional(readOnly = true)
    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String uris, Boolean unique) {
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                return ViewStatsMapper.toViewStatsDto(endpointHitRepository.getStatsWithoutUriUnique(start, end));
            } else {
                return ViewStatsMapper.toViewStatsDto(endpointHitRepository.getStatsWithoutUriNotUnique(start, end));
            }
        } else if (unique) {
            return ViewStatsMapper.toViewStatsDto(endpointHitRepository.getStatsUnique(start, end, uris));
        } else {
            return ViewStatsMapper.toViewStatsDto(endpointHitRepository.getStatsNotUnique(start, end, uris));
        }
    }
}
