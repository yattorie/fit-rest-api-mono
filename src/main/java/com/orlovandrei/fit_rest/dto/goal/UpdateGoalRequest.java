package com.orlovandrei.fit_rest.dto.goal;

import com.orlovandrei.fit_rest.entity.enums.GoalType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for updating an existing fitness goal")
public class UpdateGoalRequest {
    @NotBlank(message = "Description is required")
    @Schema(
            description = "Updated description of the goal",
            example = "Lose 6kg in 3 months"
    )
    String description;

    @NotNull(message = "Start date is required")
    @Schema(
            description = "Updated start date of the goal (yyyy-MM-dd)",
            example = "2023-06-01"
    )
    LocalDate startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in the present or future")
    @Schema(
            description = "Updated end date of the goal (must be today or later)",
            example = "2023-09-15"
    )
    LocalDate endDate;

    @NotNull(message = "Goal type is required")
    @Schema(
            description = "Updated type of the goal",
            example = "WEIGHT_LOSS"
    )
    GoalType type;
}