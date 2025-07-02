package com.orlovandrei.fit_rest.dto.exercise;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Exercise data transfer object")
public class ExerciseDto {
    @Schema(
            description = "Name of the exercise",
            example = "Bench Press"
    )
    String name;

    @Schema(
            description = "Number of sets for this exercise",
            example = "3"
    )
    Integer sets;

    @Schema(
            description = "Number of repetitions per set",
            example = "10"
    )
    Integer reps;
}