package com.orlovandrei.fit_rest.dto.meal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for creating a new meal plan")
public class CreateMealPlanRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be under 255 characters")
    @Schema(
            description = "Title of the meal plan",
            example = "Summer Weight Loss Plan"
    )
    String title;

    @Size(max = 1000, message = "Description must be under 1000 characters")
    @Schema(
            description = "Description of the meal plan",
            example = "A balanced meal plan for summer weight loss"
    )
    String description;

    @NotNull(message = "Meal entries are required")
    @Schema(
            description = "List of meal entries in this plan"
    )
    List<CreateMealEntryRequest> meals;
}