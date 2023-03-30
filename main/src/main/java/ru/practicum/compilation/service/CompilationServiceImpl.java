package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CompilationServiceImpl implements CompilationService  {
    private final CompilationRepository compilationRepository;

    @Transactional
    @Override
    public List<CompilationDto> getCompilationsByParameters(Boolean pinned, int from, int size) {
        List<Compilation> compilationList = new ArrayList<>();
        int page = from/size;
        PageRequest pageRequest = PageRequest.of(page, size);
        compilationList =compilationRepository.findCompilationsByParameters(pinned, pageRequest);
        return CompilationMapper.toCompilationDto(compilationList);
    }

    @Transactional
    @Override
    public CompilationDto getCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new BadRequestException(String.format("Запрос составлен не корректно")));
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public CompilationDto addCompilation(CompilationDto compilationDto) {
        Compilation compilation = compilationRepository.save(CompilationMapper.toCompilation(compilationDto));
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public void removeCompilation(Long compId) {
        compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException(String.format("Подборка с таким Id не найдена")));
    }
}
