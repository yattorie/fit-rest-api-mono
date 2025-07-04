package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.mapper.WorkoutMapper;
import com.orlovandrei.fit_rest.dto.workout.CreateWorkoutRequest;
import com.orlovandrei.fit_rest.dto.workout.UpdateWorkoutRequest;
import com.orlovandrei.fit_rest.dto.workout.WorkoutDto;
import com.orlovandrei.fit_rest.entity.Exercise;
import com.orlovandrei.fit_rest.entity.Workout;
import com.orlovandrei.fit_rest.exception.WorkoutNotFoundException;
import com.orlovandrei.fit_rest.repository.WorkoutRepository;
import com.orlovandrei.fit_rest.service.WorkoutService;
import com.orlovandrei.fit_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper workoutMapper;

    @Override
    @Transactional
    public WorkoutDto create(CreateWorkoutRequest request) {
        Workout workout = Workout.builder()
                .name(request.getName())
                .duration(request.getDuration())
                .difficulty(request.getDifficulty())
                .build();

        List<Exercise> exercises = request.getExercises().stream()
                .map(ex -> Exercise.builder()
                        .name(ex.getName())
                        .sets(ex.getSets())
                        .reps(ex.getReps())
                        .workout(workout)
                        .build())
                .toList();

        workout.setExercises(exercises);
        workoutRepository.save(workout);
        return workoutMapper.toDto(workout);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkoutDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return workoutRepository.findAll(pageable).map(workoutMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutDto getById(Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(Messages.WORKOUT_NOT_FOUND_BY_ID.getMessage() + id));
        return workoutMapper.toDto(workout);
    }

    @Override
    @Transactional
    public WorkoutDto update(Long id, UpdateWorkoutRequest request) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(Messages.WORKOUT_NOT_FOUND_BY_ID.getMessage() + id));

        workout.setName(request.getName());
        workout.setDuration(request.getDuration());
        workout.setDifficulty(request.getDifficulty());

        List<Exercise> updatedExercises = request.getExercises().stream()
                .map(ex -> Exercise.builder()
                        .name(ex.getName())
                        .sets(ex.getSets())
                        .reps(ex.getReps())
                        .workout(workout)
                        .build())
                .toList();

        workout.getExercises().clear();
        workout.getExercises().addAll(updatedExercises);

        return workoutMapper.toDto(workoutRepository.save(workout));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new WorkoutNotFoundException(Messages.WORKOUT_NOT_FOUND_BY_ID.getMessage() + id);
        }
        workoutRepository.deleteById(id);
    }

}

