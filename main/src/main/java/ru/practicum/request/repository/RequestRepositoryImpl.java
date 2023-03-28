package ru.practicum.request.repository;

import org.springframework.context.annotation.Lazy;
import ru.practicum.request.repository.RequestRepository;

public class RequestRepositoryImpl {
    public final RequestRepository requestRepository;
    public RequestRepositoryImpl(@Lazy RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
}
