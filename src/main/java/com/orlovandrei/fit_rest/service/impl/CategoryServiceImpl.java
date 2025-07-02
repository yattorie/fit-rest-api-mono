package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.category.CategoryDto;
import com.orlovandrei.fit_rest.dto.category.CreateCategoryRequest;
import com.orlovandrei.fit_rest.dto.category.UpdateCategoryRequest;
import com.orlovandrei.fit_rest.dto.mapper.CategoryMapper;
import com.orlovandrei.fit_rest.entity.category.Category;
import com.orlovandrei.fit_rest.exception.CategoryAlreadyExistsException;
import com.orlovandrei.fit_rest.exception.CategoryNotFoundException;
import com.orlovandrei.fit_rest.repository.CategoryRepository;
import com.orlovandrei.fit_rest.service.CategoryService;
import com.orlovandrei.fit_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDto> getAll(int page, int size) {
        return categoryRepository.findAll(PageRequest.of(page, size))
                .map(categoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(Messages.CATEGORY_NOT_FOUND_BY_ID.getMessage() + id));
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto create(CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException(Messages.CATEGORY_ALREADY_EXISTS.getMessage() + request.getName());
        }
        Category category = Category.builder()
                .name(request.getName())
                .build();
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(Messages.CATEGORY_NOT_FOUND_BY_ID.getMessage() + id));
        category.setName(request.getName());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(Messages.CATEGORY_NOT_FOUND_BY_ID.getMessage() + id);
        }
        categoryRepository.deleteById(id);
    }
}



