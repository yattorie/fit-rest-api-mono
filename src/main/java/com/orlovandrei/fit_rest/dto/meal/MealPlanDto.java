package com.orlovandrei.fit_rest.dto.meal;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MealPlanDto {
    Long id;
    String title;
    String description;
    List<MealEntryDto> meals;
}

