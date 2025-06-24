package com.orlovandrei.fit_rest.dto.mapper;

import com.orlovandrei.fit_rest.dto.meal.MealEntryDto;
import com.orlovandrei.fit_rest.dto.meal.MealPlanDto;
import com.orlovandrei.fit_rest.entity.meal.MealEntry;
import com.orlovandrei.fit_rest.entity.meal.MealPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MealPlanMapper {

    @Mapping(target = "meals", source = "meals")
    MealPlanDto toDto(MealPlan plan);

    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "recipeTitle", source = "recipe.title")
    MealEntryDto toEntryDto(MealEntry entry);

    List<MealEntryDto> toEntryDtos(List<MealEntry> entries);
}

