package ru.practicum.compilation.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.mapper.EventMapper;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompilationMapper {

    public static Compilation toCompilation(CompilationDto compilationDto) {
        return new Compilation(
            compilationDto.getId(),
                compilationDto.getPinned(),
                compilationDto.getTitle(),
                EventMapper.toEvent(compilationDto.getEvents())
        );
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle(),
                EventMapper.toEventShortDto(compilation.getEvents())
        );
    }

    public static List<Compilation> toCompilation(Iterable<CompilationDto> compilationDtos) {
        List<Compilation> compilationList = new ArrayList<>();
        for (CompilationDto compilationDto : compilationDtos) {
            compilationList.add(toCompilation(compilationDto));
        }
        return compilationList;
    }

    public static List<CompilationDto> toCompilationDto(Iterable<Compilation> compilations) {
        List<CompilationDto> compilationDtoList = new ArrayList<>();
        for (Compilation compilation : compilations) {
            compilationDtoList.add(toCompilationDto(compilation));
        }
        return compilationDtoList;
    }
}
