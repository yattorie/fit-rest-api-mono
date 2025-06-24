package com.orlovandrei.fit_rest.dto.exersice;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateExerciseRequest {
    String name;
    int sets;
    int reps;
}

