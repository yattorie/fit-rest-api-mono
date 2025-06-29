package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.category.CategoryDto;
import com.orlovandrei.fit_rest.dto.category.CreateCategoryRequest;
import com.orlovandrei.fit_rest.dto.category.UpdateCategoryRequest;
import com.orlovandrei.fit_rest.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void getAllPaged_success() {
        CategoryDto dto = new CategoryDto();
        Page<CategoryDto> page = new PageImpl<>(List.of(dto));
        Mockito.when(categoryService.getAll(0, 10)).thenReturn(page);

        ResponseEntity<Page<CategoryDto>> result = categoryController.getAllPaged(0, 10);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().getTotalElements());
    }

    @Test
    void getById_success() {
        CategoryDto dto = new CategoryDto();
        Mockito.when(categoryService.getById(1L)).thenReturn(dto);

        ResponseEntity<CategoryDto> result = categoryController.getById(1L);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void create_success() {
        CreateCategoryRequest req = new CreateCategoryRequest();
        CategoryDto dto = new CategoryDto();
        Mockito.when(categoryService.create(req)).thenReturn(dto);

        ResponseEntity<CategoryDto> result = categoryController.create(req);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void update_success() {
        UpdateCategoryRequest req = new UpdateCategoryRequest();
        CategoryDto dto = new CategoryDto();
        Mockito.when(categoryService.update(1L, req)).thenReturn(dto);

        ResponseEntity<CategoryDto> result = categoryController.update(1L, req);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void delete_success() {
        Mockito.doNothing().when(categoryService).delete(1L);

        ResponseEntity<Void> result = categoryController.delete(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        Mockito.verify(categoryService).delete(1L);
    }
}