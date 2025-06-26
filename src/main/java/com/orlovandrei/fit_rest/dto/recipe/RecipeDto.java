package com.orlovandrei.fit_rest.dto.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Recipe data transfer object")
public class RecipeDto {
    @Schema(
            description = "Unique identifier of the recipe",
            example = "1"
    )
    Long id;

    @Schema(
            description = "Title of the recipe",
            example = "Grilled Salmon with Asparagus"
    )
    String title;

    @Schema(
            description = "URL of the recipe's main image",
            example = "https://example.com/images/salmon.jpg"
    )
    String imageUrl;

    @Schema(
            description = "Description of the recipe",
            example = "A healthy and delicious salmon dish perfect for summer evenings"
    )
    String description;

    @Schema(
            description = "Step-by-step cooking instructions",
            example = "1. Preheat grill to medium-high heat\n2. Season salmon with salt and pepper\n3. Grill for 6-8 minutes per side"
    )
    String instructions;

    @Schema(
            description = "Total cooking time in minutes",
            example = "20"
    )
    Integer cookTimeMinutes;

    @Schema(
            description = "Calorie content per serving",
            example = "350"
    )
    Integer calories;

    @Schema(
            description = "Protein content in grams per serving",
            example = "30.5"
    )
    Double protein;

    @Schema(
            description = "Fat content in grams per serving",
            example = "15.2"
    )
    Double fat;

    @Schema(
            description = "Carbohydrate content in grams per serving",
            example = "12.3"
    )
    Double carbs;
}