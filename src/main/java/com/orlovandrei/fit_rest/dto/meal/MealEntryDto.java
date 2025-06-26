package com.orlovandrei.fit_rest.dto.meal;

import com.orlovandrei.fit_rest.entity.meal.MealType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Meal entry data transfer object")
public class MealEntryDto {
    @Schema(
            description = "Unique identifier of the meal entry",
            example = "1"
    )
    Long id;

    @Schema(
            description = "Type of meal (e.g., BREAKFAST, LUNCH, DINNER)",
            example = "DINNER"
    )
    MealType type;

    @Schema(
            description = "ID of the associated recipe",
            example = "7"
    )
    Long recipeId;

    @Schema(
            description = "Title of the associated recipe",
            example = "Grilled Salmon with Vegetables"
    )
    String recipeTitle;
}