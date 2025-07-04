package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.mapper.MealPlanMapper;
import com.orlovandrei.fit_rest.dto.meal.CreateMealPlanRequest;
import com.orlovandrei.fit_rest.dto.meal.MealPlanDto;
import com.orlovandrei.fit_rest.entity.MealEntry;
import com.orlovandrei.fit_rest.entity.MealPlan;
import com.orlovandrei.fit_rest.entity.Recipe;
import com.orlovandrei.fit_rest.entity.User;
import com.orlovandrei.fit_rest.exception.MealPlanNotFoundException;
import com.orlovandrei.fit_rest.exception.RecipeNotFoundException;
import com.orlovandrei.fit_rest.repository.MealPlanRepository;
import com.orlovandrei.fit_rest.repository.RecipeRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
import com.orlovandrei.fit_rest.service.MealPlanService;
import com.orlovandrei.fit_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealPlanServiceImpl implements MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final MealPlanMapper mealPlanMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<MealPlanDto> getAllByUser(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mealPlanRepository.findByUserUsername(username, pageable)
                .map(mealPlanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public MealPlanDto getById(Long id, String username) {
        MealPlan plan = mealPlanRepository.findById(id)
                .orElseThrow(() -> new MealPlanNotFoundException(Messages.MEAL_PLAN_NOT_FOUND_BY_ID.getMessage() + id));

        if (!plan.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException(Messages.MEAL_PLAN_ACCESS_DENIED.getMessage());        }

        return mealPlanMapper.toDto(plan);
    }

    @Override
    @Transactional
    public MealPlanDto create(CreateMealPlanRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage()));

        MealPlan plan = MealPlan.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();

        List<MealEntry> entries = request.getMeals().stream()
                .map(e -> {
                    Recipe recipe = recipeRepository.findById(e.getRecipeId())
                            .orElseThrow(() -> new RecipeNotFoundException(
                                    Messages.RECIPE_NOT_FOUND_BY_ID.getMessage() + e.getRecipeId()
                            ));

                    return MealEntry.builder()
                            .type(e.getType())
                            .recipe(recipe)
                            .mealPlan(plan)
                            .build();
                })
                .toList();

        plan.setMeals(entries);
        return mealPlanMapper.toDto(mealPlanRepository.save(plan));
    }

    @Override
    @Transactional
    public MealPlanDto update(Long id, CreateMealPlanRequest request, String username) {
        MealPlan plan = mealPlanRepository.findById(id)
                .orElseThrow(() -> new MealPlanNotFoundException(Messages.MEAL_PLAN_NOT_FOUND_BY_ID.getMessage() + id));

        if (!plan.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException(Messages.MEAL_PLAN_OPERATION_DENIED.getMessage());
        }

        plan.setTitle(request.getTitle());
        plan.setDescription(request.getDescription());
        plan.getMeals().clear();

        List<MealEntry> newMeals = request.getMeals().stream()
                .map(e -> {
                    Recipe recipe = recipeRepository.findById(e.getRecipeId())
                            .orElseThrow(() -> new RecipeNotFoundException(Messages.RECIPE_NOT_FOUND_BY_ID.getMessage() + e.getRecipeId()));

                    return MealEntry.builder()
                            .type(e.getType())
                            .recipe(recipe)
                            .mealPlan(plan)
                            .build();
                })
                .toList();

        plan.getMeals().addAll(newMeals);
        return mealPlanMapper.toDto(mealPlanRepository.save(plan));
    }

    @Override
    @Transactional
    public void delete(Long id, String username) {
        MealPlan plan = mealPlanRepository.findById(id)
                .orElseThrow(() -> new MealPlanNotFoundException(Messages.MEAL_PLAN_NOT_FOUND_BY_ID.getMessage() + id));

        if (!plan.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException(Messages.MEAL_PLAN_OPERATION_DENIED.getMessage());
        }

        mealPlanRepository.delete(plan);
    }
}