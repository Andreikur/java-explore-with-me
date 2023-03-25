package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto);
    List<CategoryDto> getCategories();
    void removeCategory(long categoryId);
}
