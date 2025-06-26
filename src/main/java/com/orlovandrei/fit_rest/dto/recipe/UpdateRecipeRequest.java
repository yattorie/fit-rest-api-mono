package com.orlovandrei.fit_rest.dto.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for updating an existing recipe")
public class UpdateRecipeRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be under 255 characters")
    @Schema(
            description = "Updated title of the recipe",
            example = "Grilled Salmon with Lemon and Asparagus"
    )
    String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must be under 2000 characters")
    @Schema(
            description = "Updated description of the recipe",
            example = "A healthy and delicious salmon dish with a hint of lemon, perfect for summer evenings"
    )
    String description;

    @NotBlank(message = "Instructions are required")
    @Size(max = 5000, message = "Instructions must be under 5000 characters")
    @Schema(
            description = "Updated step-by-step cooking instructions",
            example = "1. Preheat grill to medium-high heat\n2. Season salmon with salt, pepper, and lemon zest\n3. Grill for 6-8 minutes per side"
    )
    String instructions;

    @NotNull(message = "Cook time is required")
    @Min(value = 1, message = "Cook time must be at least 1 minute")
    @Schema(
            description = "Updated cooking time in minutes",
            example = "25"
    )
    Integer cookTimeMinutes;

    @Min(value = 0, message = "Calories must be zero or greater")
    @Schema(
            description = "Updated calorie content per serving",
            example = "360"
    )
    Integer calories;

    @Min(value = 0, message = "Protein must be zero or greater")
    @Schema(
            description = "Updated protein content in grams per serving",
            example = "32.0"
    )
    Double protein;

    @Min(value = 0, message = "Fat must be zero or greater")
    @Schema(
            description = "Updated fat content in grams per serving",
            example = "16.0"
    )
    Double fat;

    @Min(value = 0, message = "Carbs must be zero or greater")
    @Schema(
            description = "Updated carbohydrate content in grams per serving",
            example = "13.5"
    )
    Double carbs;
}