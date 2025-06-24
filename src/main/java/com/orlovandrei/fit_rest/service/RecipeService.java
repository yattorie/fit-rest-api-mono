package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.recipe.CreateRecipeRequest;
import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.dto.recipe.UpdateRecipeRequest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface RecipeService {
    RecipeDto create(CreateRecipeRequest request);
    Page<RecipeDto> getAll(int page, int size);
    RecipeDto getById(Long id);
    RecipeDto update(Long id, UpdateRecipeRequest request);
    @Transactional
    void delete(Long id);
}

