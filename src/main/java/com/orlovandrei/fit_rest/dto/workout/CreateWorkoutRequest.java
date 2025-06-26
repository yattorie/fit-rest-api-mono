package com.orlovandrei.fit_rest.dto.workout;

import com.orlovandrei.fit_rest.dto.exersice.CreateExerciseRequest;
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
@Schema(description = "Request for creating a new workout program")
public class CreateWorkoutRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be under 255 characters")
    @Schema(
            description = "Name of the workout program",
            example = "Full Body Strength Training"
    )
    String name;

    @NotNull(message = "Duration is required")
    @Schema(
            description = "Total duration of the workout in ISO-8601 format (e.g., PT1H30M)",
            example = "PT45M"
    )
    Duration duration;

    @NotNull(message = "Difficulty level is required")
    @Schema(
            description = "Difficulty level of the workout",
            example = "INTERMEDIATE"
    )
    WorkoutDifficulty difficulty;

    @NotNull(message = "Exercises list is required")
    @Schema(
            description = "List of exercises in this workout"
    )
    List<CreateExerciseRequest> exercises;
}