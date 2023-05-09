 package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.endpointHit.EndpointHitDto;


@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EndpointHitClient {
    private static final String API_PREFIX = "/hit";
    private final WebClient webClient;

    @Transactional
    public ResponseEntity<Object> addStats(EndpointHitDto endpointHitDto) {
        return webClient.post()
                .uri(API_PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(endpointHitDto))
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.CREATED)) {
                        return response.bodyToMono(Object.class)
                                .map(body -> ResponseEntity.status(HttpStatus.CREATED).body(body));
                    } else {
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                })
                .block();
    }
}
