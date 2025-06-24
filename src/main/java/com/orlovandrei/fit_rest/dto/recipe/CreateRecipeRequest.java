package com.orlovandrei.fit_rest.dto.recipe;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateRecipeRequest {
    String title;
    String description;
    String instructions;
    Integer cookTimeMinutes;
    Integer calories;
    Double protein;
    Double fat;
    Double carbs;
}

