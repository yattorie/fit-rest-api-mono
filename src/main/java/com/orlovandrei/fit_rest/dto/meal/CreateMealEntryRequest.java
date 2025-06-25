package com.orlovandrei.fit_rest.dto.meal;

import com.orlovandrei.fit_rest.entity.meal.MealType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMealEntryRequest {
    @NotNull(message = "Meal type is required")
    MealType type;

    @NotNull(message = "Recipe ID is required")
    Long recipeId;
}

