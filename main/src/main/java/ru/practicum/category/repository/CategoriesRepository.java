package ru.practicum.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.practicum.category.model.Category;

import java.util.List;

@RepositoryRestResource(path = "categoriesRepository")
public interface CategoriesRepository extends JpaRepository<Category, Long> {

    @Query("SELECT b FROM Category b ")
    List<Category> findAllPage(Pageable pageable);

    Page<Category> findAll(Pageable pageable);
}
