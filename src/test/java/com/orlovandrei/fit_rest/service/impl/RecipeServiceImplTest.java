package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.mapper.RecipeMapper;
import com.orlovandrei.fit_rest.dto.recipe.CreateRecipeRequest;
import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.dto.recipe.UpdateRecipeRequest;
import com.orlovandrei.fit_rest.entity.Recipe;
import com.orlovandrei.fit_rest.exception.ArticleNotFoundException;
import com.orlovandrei.fit_rest.exception.RecipeNotFoundException;
import com.orlovandrei.fit_rest.repository.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeMapper recipeMapper;
    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    void create_success() {
        CreateRecipeRequest request = new CreateRecipeRequest();
        request.setTitle("t");
        request.setDescription("d");
        request.setInstructions("i");
        request.setCookTimeMinutes(10);
        request.setCalories(300);
        request.setProtein(20.0);
        request.setFat(10.0);
        request.setCarbs(30.0);

        Recipe recipe = Recipe.builder().title("t").description("d").instructions("i").build();
        RecipeDto dto = new RecipeDto();

        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        Mockito.when(recipeMapper.toDto(any(Recipe.class))).thenReturn(dto);

        RecipeDto result = recipeService.create(request);

        Assertions.assertNotNull(result);
    }

    @Test
    void update_success() {
        UpdateRecipeRequest request = new UpdateRecipeRequest();
        request.setTitle("t");
        request.setDescription("d");
        request.setInstructions("i");
        request.setCookTimeMinutes(20);
        request.setCalories(400);
        request.setProtein(21.0);
        request.setFat(11.0);
        request.setCarbs(31.0);

        Recipe recipe = Recipe.builder()
                .id(5L)
                .title("oldTitle")
                .build();
        Mockito.when(recipeRepository.findById(5L)).thenReturn(Optional.of(recipe));
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        Mockito.when(recipeMapper.toDto(any(Recipe.class))).thenReturn(new RecipeDto());

        RecipeDto result = recipeService.update(5L, request);

        Assertions.assertNotNull(result);
    }

    @Test
    void update_throwsRecipeNotFound() {
        UpdateRecipeRequest request = new UpdateRecipeRequest();
        Mockito.when(recipeRepository.findById(5L)).thenReturn(Optional.empty());
        Assertions.assertThrows(RecipeNotFoundException.class, () -> recipeService.update(5L, request));
    }

    @Test
    void getAll() {
        Page<Recipe> page = new PageImpl<>(List.of(Recipe.builder().build()));
        Mockito.when(recipeRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(recipeMapper.toDto(any(Recipe.class))).thenReturn(new RecipeDto());

        Page<RecipeDto> result = recipeService.getAll(0, 10);
        Assertions.assertEquals(1, result.getTotalElements());
    }

    @Test
    void getById_success() {
        Recipe recipe = Recipe.builder().id(42L).build();
        Mockito.when(recipeRepository.findById(42L)).thenReturn(Optional.of(recipe));
        Mockito.when(recipeMapper.toDto(any(Recipe.class))).thenReturn(new RecipeDto());

        RecipeDto result = recipeService.getById(42L);

        Assertions.assertNotNull(result);
    }

    @Test
    void getById_throwsRecipeNotFound() {
        Mockito.when(recipeRepository.findById(42L)).thenReturn(Optional.empty());
        Assertions.assertThrows(RecipeNotFoundException.class, () -> recipeService.getById(42L));
    }

    @Test
    void delete_success() {
        Mockito.when(recipeRepository.existsById(5L)).thenReturn(true);
        recipeService.delete(5L);
        Mockito.verify(recipeRepository).deleteById(5L);
    }

    @Test
    void delete_throwsArticleNotFound() {
        Mockito.when(recipeRepository.existsById(5L)).thenReturn(false);
        Assertions.assertThrows(ArticleNotFoundException.class, () -> recipeService.delete(5L));
    }
}