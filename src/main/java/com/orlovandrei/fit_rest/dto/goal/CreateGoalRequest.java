package com.orlovandrei.fit_rest.dto.goal;

import com.orlovandrei.fit_rest.entity.goal.GoalType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for creating a new fitness goal")
public class CreateGoalRequest {
    @NotBlank(message = "Description is required")
    @Schema(
            description = "Description of the goal",
            example = "Lose 5kg in 3 months"
    )
    String description;

    @NotNull(message = "Start date is required")
    @Schema(
            description = "Start date of the goal (yyyy-MM-dd)",
            example = "2023-06-01"
    )
    LocalDate startDate;

    @NotNull(message = "End date is required")
    @Schema(
            description = "End date of the goal (yyyy-MM-dd)",
            example = "2023-08-31"
    )
    LocalDate endDate;

    @NotNull(message = "Goal type is required")
    @Schema(
            description = "Type of the goal",
            example = "WEIGHT_LOSS"
    )
    GoalType type;
}