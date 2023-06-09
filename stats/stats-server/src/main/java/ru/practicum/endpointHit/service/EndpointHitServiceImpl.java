package ru.practicum.endpointHit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.endpointHit.EndpointHitDto;
import ru.practicum.endpointHit.model.EndpointHit;
import ru.practicum.endpointHit.repository.EndpointHitRepository;
import ru.practicum.endpointHit.mapper.EndpointHitMapper;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EndpointHitServiceImpl implements EndpointHitService  {
    private final EndpointHitRepository endpointHitRepository;

    @Transactional
    @Override
    public EndpointHitDto addEndpointHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitRepository.save(EndpointHitMapper.toEndpointHit(endpointHitDto));
        log.info("EndpointHit сохранен в БД   ", endpointHit);
        return EndpointHitMapper.toEndpointHitDto(endpointHit);
    }

}
