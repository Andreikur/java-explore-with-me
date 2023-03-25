package ru.practicum.category.repository;

import org.springframework.context.annotation.Lazy;

public class CategoriesRepositoryImpl {
    private final CategoriesRepository categoriesRepository;
    public CategoriesRepositoryImpl (@Lazy CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }
}
