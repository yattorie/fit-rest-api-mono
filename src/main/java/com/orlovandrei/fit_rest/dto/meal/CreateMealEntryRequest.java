package com.orlovandrei.fit_rest.dto.meal;

import com.orlovandrei.fit_rest.entity.meal.MealType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for creating a meal entry within a meal plan")
public class CreateMealEntryRequest {
    @NotNull(message = "Meal type is required")
    @Schema(
            description = "Type of meal (e.g., BREAKFAST, LUNCH, DINNER)",
            example = "LUNCH"
    )
    MealType type;

    @NotNull(message = "Recipe ID is required")
    @Schema(
            description = "ID of the recipe for this meal entry",
            example = "5"
    )
    Long recipeId;
}