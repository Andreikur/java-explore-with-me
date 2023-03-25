package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.repository.CategoriesRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryServiceImpl implements CategoryService {
    private final CategoriesRepository categoriesRepository;


    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public List<CategoryDto> getCategories() {
        return null;
    }

    @Override
    public void removeCategory(long categoryId) {

    }
}
