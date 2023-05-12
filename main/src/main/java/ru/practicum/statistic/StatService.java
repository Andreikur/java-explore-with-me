package ru.practicum.statistic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.client.EndpointHitClient;
import ru.practicum.client.ViewStatsClient;
import ru.practicum.endpointHit.EndpointHitDto;

import java.util.List;

@Slf4j
@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatService {
    private final ViewStatsClient viewStatsClient;
    private final EndpointHitClient endpointHitClient;

    public ResponseEntity<Object> getViewStats(
            String rangeStart,
            String rangeEnd,
            List<String> uris,
            Boolean unique) {
        log.info("StatsService - method call 'getViewStats' with params: rangeStart={}, rangeEnd={}, uris={}, " +
                "unique={}", rangeStart, rangeEnd, uris, unique);
        return viewStatsClient.getViewStats(rangeStart, rangeEnd, uris, unique);
    }

    //@Transactional
    public void createView(EndpointHitDto endpointHitDto) {
        log.info("StatsService - method call 'createView' with params: endpointHitDto={}", endpointHitDto);
        endpointHitClient.addStats(endpointHitDto);
    }
}
