package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.recipe.CreateRecipeRequest;
import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.dto.recipe.UpdateRecipeRequest;
import com.orlovandrei.fit_rest.service.MinioService;
import com.orlovandrei.fit_rest.service.RecipeService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private RecipeController recipeController;

    @Test
    void create_success() {
        CreateRecipeRequest request = new CreateRecipeRequest();
        RecipeDto dto = new RecipeDto();
        Mockito.when(recipeService.create(request)).thenReturn(dto);

        ResponseEntity<RecipeDto> result = recipeController.create(request);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void getAll_success() {
        RecipeDto dto = new RecipeDto();
        Page<RecipeDto> page = new PageImpl<>(List.of(dto));
        Mockito.when(recipeService.getAll(0, 10)).thenReturn(page);

        ResponseEntity<Page<RecipeDto>> result = recipeController.getAll(0, 10);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().getTotalElements());
    }

    @Test
    void getById_success() {
        RecipeDto dto = new RecipeDto();
        Mockito.when(recipeService.getById(1L)).thenReturn(dto);

        ResponseEntity<RecipeDto> result = recipeController.getById(1L);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void uploadImage_success() {
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(minioService.uploadRecipeImage(file, 1L)).thenReturn("url");

        ResponseEntity<String> result = recipeController.uploadImage(1L, file);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals("url", result.getBody());
    }

    @Test
    void delete_success() {
        Mockito.doNothing().when(recipeService).delete(1L);

        ResponseEntity<Void> result = recipeController.delete(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        Mockito.verify(recipeService).delete(1L);
    }

    @Test
    void update_success() {
        UpdateRecipeRequest request = new UpdateRecipeRequest();
        RecipeDto dto = new RecipeDto();
        Mockito.when(recipeService.update(2L, request)).thenReturn(dto);

        ResponseEntity<RecipeDto> result = recipeController.update(2L, request);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }
}