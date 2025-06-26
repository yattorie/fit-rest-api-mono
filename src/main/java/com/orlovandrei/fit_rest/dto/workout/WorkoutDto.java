package com.orlovandrei.fit_rest.dto.workout;

import com.orlovandrei.fit_rest.dto.exersice.ExerciseDto;
import com.orlovandrei.fit_rest.entity.workout.WorkoutDifficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Workout program data transfer object")
public class WorkoutDto {
    @Schema(
            description = "Unique identifier of the workout",
            example = "1"
    )
    Long id;

    @Schema(
            description = "Name of the workout program",
            example = "Full Body Strength Training"
    )
    String name;

    @Schema(
            description = "Total duration of the workout in ISO-8601 format (e.g., PT1H30M)",
            example = "PT45M"
    )
    Duration duration;

    @Schema(
            description = "Difficulty level of the workout",
            example = "INTERMEDIATE"
    )
    WorkoutDifficulty difficulty;

    @Schema(
            description = "List of exercises in this workout"
    )
    List<ExerciseDto> exercises;
}