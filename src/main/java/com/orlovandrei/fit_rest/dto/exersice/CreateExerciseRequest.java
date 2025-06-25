package com.orlovandrei.fit_rest.dto.exersice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateExerciseRequest {
    @NotBlank(message = "Name is required")
    String name;

    @NotNull(message = "Sets count is required")
    @Min(value = 1, message = "Minimum 1 set required")
    Integer sets;

    @NotNull(message = "Reps count is required")
    @Min(value = 1, message = "Minimum 1 rep required")
    Integer reps;
}

