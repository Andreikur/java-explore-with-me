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
        return new Category(
                categoryDto.getId(),
                categoryDto.getName()
        );
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public static List<Category> toCategory(Iterable<CategoryDto> categoryDtos) {
        List<Category> categoryList =new ArrayList<>();
        for (CategoryDto categoryDto : categoryDtos) {
            categoryList.add(toCategory(categoryDto));
        }
        return categoryList;
    }

    public static List<CategoryDto> toCategoryDto(Iterable<Category> categories) {
        List<CategoryDto> categoryDtoList =new ArrayList<>();
        for (Category category : categories) {
            categoryDtoList.add(toCategoryDto(category));
        }
        return categoryDtoList;
    }
}
