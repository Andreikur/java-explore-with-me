package ru.practicum.category.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategoryMapper {
    public static Category toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static List<Category> toCategory(Iterable<CategoryDto> categoryDtos) {
        List<Category> categoryList = new ArrayList<>();
        for (CategoryDto categoryDto : categoryDtos) {
            categoryList.add(toCategory(categoryDto));
        }
        return categoryList;
    }

    public static List<CategoryDto> toCategoryDto(Iterable<Category> categories) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : categories) {
            categoryDtoList.add(toCategoryDto(category));
        }
        return categoryDtoList;
    }
}
