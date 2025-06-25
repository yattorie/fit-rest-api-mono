package com.orlovandrei.fit_rest.dto.goal;

import com.orlovandrei.fit_rest.entity.goal.GoalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateGoalRequest {
    @NotBlank(message = "Description is required")
    String description;

    @NotNull(message = "Start date is required")
    LocalDate startDate;

    @NotNull(message = "End date is required")
    LocalDate endDate;

    @NotNull(message = "Goal type is required")
    GoalType type;
}


