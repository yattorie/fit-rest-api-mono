package com.orlovandrei.fit_rest.dto.mapper;

import com.orlovandrei.fit_rest.dto.recipe.RecipeDto;
import com.orlovandrei.fit_rest.entity.recipe.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    RecipeDto toDto(Recipe recipe);
}

