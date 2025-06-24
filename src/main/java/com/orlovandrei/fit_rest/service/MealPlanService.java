package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.meal.CreateMealPlanRequest;
import com.orlovandrei.fit_rest.dto.meal.MealPlanDto;
import org.springframework.data.domain.Page;

public interface MealPlanService {
    Page<MealPlanDto> getAllByUser(String username, int page, int size);
    MealPlanDto getById(Long id, String username);
    MealPlanDto create(CreateMealPlanRequest request, String username);
    MealPlanDto update(Long id, CreateMealPlanRequest request, String username);
    void delete(Long id, String username);
}
