package ru.practicum.endpointHit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.endpointHit.EndpointHitDto;
import ru.practicum.endpointHit.service.EndpointHitService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/hit")
public class EndpointHitController {
    private final EndpointHitService endpointHitService;

    @PostMapping
    public EndpointHitDto addEndpointHit(@Valid @RequestBody EndpointHitDto endpointHitDto){
        return endpointHitService.addEndpointHit(endpointHitDto);
    }
}
