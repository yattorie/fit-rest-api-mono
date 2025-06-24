package com.orlovandrei.fit_rest.dto.meal;

import com.orlovandrei.fit_rest.entity.meal.MealType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMealEntryRequest {
    MealType type;
    Long recipeId;
}

