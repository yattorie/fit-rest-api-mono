package com.orlovandrei.fit_rest.dto.exersice;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExerciseDto {
    String name;
    int sets;
    int reps;
}

