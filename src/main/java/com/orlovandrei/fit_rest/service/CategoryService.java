package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.category.CategoryDto;
import com.orlovandrei.fit_rest.dto.category.CreateCategoryRequest;
import com.orlovandrei.fit_rest.dto.category.UpdateCategoryRequest;
import org.springframework.data.domain.Page;


public interface CategoryService {
    Page<CategoryDto> getAll(int page, int size);
    CategoryDto getById(Long id);
    CategoryDto create(CreateCategoryRequest request);
    CategoryDto update(Long id, UpdateCategoryRequest request);
    void delete(Long id);

}

