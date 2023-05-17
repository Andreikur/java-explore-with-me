 package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ViewStatsClient {
    private static final String API_PREFIX = "/stats";
     private final WebClient webClient;


     public ResponseEntity<Object> getViewStats(String start, String end, List<String> uris, Boolean unique) {
         String paramsUri = uris.stream().reduce("", (result, uri) -> result + "&uris=" + uri);

         return webClient.get()
                 .uri(uriBuilder -> uriBuilder
                         .path(API_PREFIX)
                         .queryParam("start", start)
                         .queryParam("end", end)
                         .query(paramsUri)
                         .queryParam("unique", unique)
                         .build())
                 .exchangeToMono(response -> {
                     if (response.statusCode().is2xxSuccessful()) {
                         return response.bodyToMono(Object.class)
                                 .map(body -> ResponseEntity.ok().body(body));
                     } else {
                         return response.createException()
                                 .flatMap(Mono::error);
                     }
                 })
                 .block();
     }

}
