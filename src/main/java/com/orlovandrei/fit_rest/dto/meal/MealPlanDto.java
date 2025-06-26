package com.orlovandrei.fit_rest.dto.meal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Meal plan data transfer object")
public class MealPlanDto {
    @Schema(
            description = "Unique identifier of the meal plan",
            example = "3"
    )
    Long id;

    @Schema(
            description = "Title of the meal plan",
            example = "Summer Weight Loss Plan"
    )
    String title;

    @Schema(
            description = "Description of the meal plan",
            example = "A balanced meal plan for summer weight loss"
    )
    String description;

    @Schema(
            description = "List of meal entries in this plan"
    )
    List<MealEntryDto> meals;
}