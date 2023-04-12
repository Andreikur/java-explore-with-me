package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getCompilationsByParameters(Boolean pinned, int from, int size);
    CompilationDto getCompilation(Long compId);
    CompilationDto addCompilation(NewCompilationDto newCompilationDto);
    void removeCompilation(Long compId);
    CompilationDto updateCompilation(Long compId, NewCompilationDto newCompilationDto);
}
