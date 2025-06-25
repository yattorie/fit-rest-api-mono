package com.orlovandrei.fit_rest.dto.meal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMealPlanRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be under 255 characters")
    String title;

    @Size(max = 1000, message = "Description must be under 1000 characters")
    String description;

    @NotNull(message = "Meal entries are required")
    List<CreateMealEntryRequest> meals;
}

