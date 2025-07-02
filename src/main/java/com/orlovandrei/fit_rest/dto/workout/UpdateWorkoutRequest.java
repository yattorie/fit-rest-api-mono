package com.orlovandrei.fit_rest.dto.workout;

import com.orlovandrei.fit_rest.dto.exercise.CreateExerciseRequest;
import com.orlovandrei.fit_rest.entity.workout.WorkoutDifficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for updating an existing workout program")
public class UpdateWorkoutRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be under 255 characters")
    @Schema(
            description = "Updated name of the workout program",
            example = "Advanced Full Body Strength Training"
    )
    String name;

    @NotNull(message = "Duration is required")
    @Schema(
            description = "Updated duration of the workout in ISO-8601 format (e.g., PT1H30M)",
            example = "PT60M"
    )
    Duration duration;

    @NotNull(message = "Difficulty level is required")
    @Schema(
            description = "Updated difficulty level of the workout",
            example = "ADVANCED"
    )
    WorkoutDifficulty difficulty;

    @NotNull(message = "Exercises list is required")
    @Schema(
            description = "Updated list of exercises in this workout"
    )
    List<CreateExerciseRequest> exercises;
}