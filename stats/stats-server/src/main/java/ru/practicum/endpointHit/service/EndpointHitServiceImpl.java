package ru.practicum.endpointHit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.endpointHit.dto.EndpointHitDto;
import ru.practicum.endpointHit.model.EndpointHit;
import ru.practicum.endpointHit.repository.EndpointHitRepository;
import ru.practicum.endpointHit.mapper.EndpointHitMapper;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EndpointHitServiceImpl {
    private  final EndpointHitRepository endpointHitRepository;

    public EndpointHitDto addEndpointHit(EndpointHitDto endpointHitDto){
        EndpointHit endpointHit = endpointHitRepository.save(EndpointHitMapper.toEndpointHit(endpointHitDto));
        return EndpointHitMapper.toEndpointHitDto(endpointHit);
    }
}
