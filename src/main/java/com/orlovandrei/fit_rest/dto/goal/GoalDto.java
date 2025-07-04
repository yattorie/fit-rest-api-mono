package com.orlovandrei.fit_rest.dto.goal;

import com.orlovandrei.fit_rest.entity.enums.GoalType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Fitness goal data transfer object")
public class GoalDto {
    @Schema(
            description = "Unique identifier of the goal",
            example = "1"
    )
    Long id;

    @Schema(
            description = "Description of the goal",
            example = "Lose 5kg in 3 months"
    )
    String description;

    @Schema(
            description = "Start date of the goal (yyyy-MM-dd)",
            example = "2023-06-01"
    )
    LocalDate startDate;

    @Schema(
            description = "End date of the goal (yyyy-MM-dd)",
            example = "2023-08-31"
    )
    LocalDate endDate;

    @Schema(
            description = "Type of the goal",
            example = "WEIGHT_LOSS"
    )
    GoalType type;
}