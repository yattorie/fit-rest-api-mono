package com.orlovandrei.fit_rest.dto.mapper;

import com.orlovandrei.fit_rest.dto.exercise.ExerciseDto;
import com.orlovandrei.fit_rest.dto.workout.WorkoutDto;
import com.orlovandrei.fit_rest.entity.Exercise;
import com.orlovandrei.fit_rest.entity.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkoutMapper {

    @Mapping(target = "exercises", source = "exercises")
    WorkoutDto toDto(Workout workout);

    List<ExerciseDto> toExerciseDtos(List<Exercise> exercises);
}

