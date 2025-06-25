package com.orlovandrei.fit_rest.dto.workout;

import com.orlovandrei.fit_rest.dto.exersice.CreateExerciseRequest;
import com.orlovandrei.fit_rest.entity.workout.WorkoutDifficulty;
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
public class UpdateWorkoutRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be under 255 characters")
    String name;

    @NotNull(message = "Duration is required")
    Duration duration;

    @NotNull(message = "Difficulty level is required")
    WorkoutDifficulty difficulty;

    @NotNull(message = "Exercises list is required")
    List<CreateExerciseRequest> exercises;
}

