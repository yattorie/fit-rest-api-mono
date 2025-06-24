package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.workout.CreateWorkoutRequest;
import com.orlovandrei.fit_rest.dto.workout.UpdateWorkoutRequest;
import com.orlovandrei.fit_rest.dto.workout.WorkoutDto;
import org.springframework.data.domain.Page;

public interface WorkoutService {
    WorkoutDto create(CreateWorkoutRequest request);
    Page<WorkoutDto> getAll(int page, int size);
    WorkoutDto getById(Long id);
    WorkoutDto update(Long id, UpdateWorkoutRequest request);
    void delete(Long id);

}

