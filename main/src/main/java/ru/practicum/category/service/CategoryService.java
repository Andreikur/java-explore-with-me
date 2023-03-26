package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(long catId, CategoryDto categoryDto);
    List<CategoryDto> getCategories(int from, int size);
    CategoryDto getCategory(Long catId);
    void removeCategory(long catId);
}
