package com.orlovandrei.fit_rest.dto.recipe;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipeDto {
    Long id;
    String title;
    String imageUrl;
    String description;
    String instructions;
    Integer cookTimeMinutes;
    Integer calories;
    Double protein;
    Double fat;
    Double carbs;
}

