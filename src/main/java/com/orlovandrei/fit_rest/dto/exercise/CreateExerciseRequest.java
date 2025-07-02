package com.orlovandrei.fit_rest.dto.exercise;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for creating a new exercise")
public class CreateExerciseRequest {
    @NotBlank(message = "Name is required")
    @Schema(
            description = "Name of the exercise",
            example = "Squats"
    )
    String name;

    @NotNull(message = "Sets count is required")
    @Min(value = 1, message = "Minimum 1 set required")
    @Schema(
            description = "Number of sets for this exercise",
            example = "4"
    )
    Integer sets;

    @NotNull(message = "Reps count is required")
    @Min(value = 1, message = "Minimum 1 rep required")
    @Schema(
            description = "Number of repetitions per set",
            example = "12"
    )
    Integer reps;
}