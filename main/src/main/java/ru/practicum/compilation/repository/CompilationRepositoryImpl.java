package ru.practicum.compilation.repository;

import org.springframework.context.annotation.Lazy;

public class CompilationRepositoryImpl {
    private final CompilationRepository compilationRepository;

    public CompilationRepositoryImpl(@Lazy CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }
}
