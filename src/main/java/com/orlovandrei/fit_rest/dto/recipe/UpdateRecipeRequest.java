package com.orlovandrei.fit_rest.dto.recipe;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRecipeRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be under 255 characters")
    String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must be under 2000 characters")
    String description;

    @NotBlank(message = "Instructions are required")
    @Size(max = 5000, message = "Instructions must be under 5000 characters")
    String instructions;

    @NotNull(message = "Cook time is required")
    @Min(value = 1, message = "Cook time must be at least 1 minute")
    Integer cookTimeMinutes;

    @Min(value = 0, message = "Calories must be zero or greater")
    Integer calories;

    @Min(value = 0, message = "Protein must be zero or greater")
    Double protein;

    @Min(value = 0, message = "Fat must be zero or greater")
    Double fat;

    @Min(value = 0, message = "Carbs must be zero or greater")
    Double carbs;
}
