package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.mapper.RecipeMapper;
import com.orlovandrei.fit_rest.dto.recipe.CreateRecipeRequest;
import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.dto.recipe.UpdateRecipeRequest;
import com.orlovandrei.fit_rest.entity.recipe.Recipe;
import com.orlovandrei.fit_rest.exception.ArticleNotFoundException;
import com.orlovandrei.fit_rest.exception.RecipeAlreadyExistsException;
import com.orlovandrei.fit_rest.exception.RecipeNotFoundException;
import com.orlovandrei.fit_rest.repository.RecipeRepository;
import com.orlovandrei.fit_rest.service.RecipeService;
import com.orlovandrei.fit_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Override
    @Transactional
    public RecipeDto create(CreateRecipeRequest request) {
        if (recipeRepository.existsByTitle(request.getTitle())) {
            throw new RecipeAlreadyExistsException(Messages.RECIPE_ALREADY_EXISTS.getMessage() + request.getTitle());
        }

        Recipe recipe = Recipe.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .instructions(request.getInstructions())
                .cookTimeMinutes(request.getCookTimeMinutes())
                .calories(request.getCalories())
                .protein(request.getProtein())
                .fat(request.getFat())
                .carbs(request.getCarbs())
                .build();


        recipeRepository.save(recipe);
        return recipeMapper.toDto(recipe);
    }

    @Override
    @Transactional
    public RecipeDto update(Long id, UpdateRecipeRequest request) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(Messages.RECIPE_NOT_FOUND_BY_ID.getMessage() + id));

        if (!recipe.getTitle().equals(request.getTitle()) && recipeRepository.existsByTitleAndIdNot(request.getTitle(), id)) {
            throw new RecipeAlreadyExistsException(Messages.RECIPE_ALREADY_EXISTS.getMessage() + request.getTitle());
        }

        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setInstructions(request.getInstructions());
        recipe.setCookTimeMinutes(request.getCookTimeMinutes());
        recipe.setCalories(request.getCalories());
        recipe.setProtein(request.getProtein());
        recipe.setFat(request.getFat());
        recipe.setCarbs(request.getCarbs());

        return recipeMapper.toDto(recipeRepository.save(recipe));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recipeRepository.findAll(pageable).map(recipeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDto getById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(Messages.RECIPE_NOT_FOUND_BY_ID.getMessage() + id));
        return recipeMapper.toDto(recipe);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new ArticleNotFoundException(Messages.RECIPE_NOT_FOUND_BY_ID.getMessage() + id);
        }
        recipeRepository.deleteById(id);
    }
}

