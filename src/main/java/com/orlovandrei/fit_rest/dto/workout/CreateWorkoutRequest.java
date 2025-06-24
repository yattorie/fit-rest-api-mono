package com.orlovandrei.fit_rest.dto.workout;

import com.orlovandrei.fit_rest.dto.exersice.CreateExerciseRequest;
import com.orlovandrei.fit_rest.entity.workout.WorkoutDifficulty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateWorkoutRequest {
    String name;
    Duration duration;
    WorkoutDifficulty difficulty;
    List<CreateExerciseRequest> exercises;
}

