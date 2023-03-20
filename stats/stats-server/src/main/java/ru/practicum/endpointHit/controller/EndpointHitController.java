package ru.practicum.endpointHit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.endpointHit.EndpointHitDto;
import ru.practicum.endpointHit.service.EndpointHitService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/hit")
public class EndpointHitController {
    private final EndpointHitService endpointHitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto addEndpointHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        return endpointHitService.addEndpointHit(endpointHitDto);
    }
}
