package ru.practicum.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.compilation.model.Compilation;

import java.util.List;

@RepositoryRestResource(path = "compilationRepository")
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Query("SELECT c FROM Compilation c " +
            "WHERE c.pinned = ?1")
    List<Compilation> findCompilationsByParameters(Boolean pinned, Pageable pageable);
}
