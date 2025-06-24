package com.orlovandrei.fit_rest.dto.goal;

import com.orlovandrei.fit_rest.entity.goal.GoalType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateGoalRequest {
    String description;
    LocalDate startDate;
    LocalDate endDate;
    GoalType type;
}


