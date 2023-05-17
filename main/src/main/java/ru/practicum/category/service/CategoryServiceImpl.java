package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoriesRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.CategoryUsedException;
import ru.practicum.exception.MailNotUniqueException;
import ru.practicum.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryServiceImpl implements CategoryService {
    private final CategoriesRepository categoriesRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = categoriesRepository.save(CategoryMapper.toCategory(categoryDto));
        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(long catId, CategoryDto categoryDto) {

        try {
            Category category = categoriesRepository.findById(catId).orElseThrow(() ->
                    new NotFoundException(String.format("Категория с таким Id не найдена")));
            category.setName(categoryDto.getName());
            return CategoryMapper.toCategoryDto(category);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new MailNotUniqueException(String.format(
                        "Имя категории не уникально"));
            }
        }

        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        List<Category> categoryList = new ArrayList<>();
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        categoryList.addAll(categoriesRepository.findAll(pageRequest).toList());
        return CategoryMapper.toCategoryDto(categoryList);
    }


    @Transactional(readOnly = true)
    @Override
    public CategoryDto getCategory(Long catId) {
        Category category = categoriesRepository.findById(catId).orElseThrow(() ->
                new NotFoundException(String.format("Категория с таким Id не найдена")));
        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    @Override
    public void removeCategory(long catId) {
        categoriesRepository.findById(catId).orElseThrow(() ->
                new NotFoundException(String.format("Категория с таким Id не найдена")));
        if (eventRepository.existsByCategoryId(catId)) {
            throw new CategoryUsedException("Категория используется");
        }
        categoriesRepository.deleteById(catId);
    }
}
