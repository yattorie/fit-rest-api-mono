package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.mapper.WorkoutMapper;
import com.orlovandrei.fit_rest.dto.workout.CreateWorkoutRequest;
import com.orlovandrei.fit_rest.dto.workout.UpdateWorkoutRequest;
import com.orlovandrei.fit_rest.dto.workout.WorkoutDto;
import com.orlovandrei.fit_rest.entity.Workout;
import com.orlovandrei.fit_rest.entity.enums.WorkoutDifficulty;
import com.orlovandrei.fit_rest.exception.WorkoutNotFoundException;
import com.orlovandrei.fit_rest.repository.WorkoutRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceImplTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private WorkoutMapper workoutMapper;

    @InjectMocks
    private WorkoutServiceImpl workoutService;

    @Test
    void create_success() {
        CreateWorkoutRequest request = new CreateWorkoutRequest();
        request.setName("Test Workout");
        request.setDuration(Duration.ofMinutes(45));
        request.setDifficulty(WorkoutDifficulty.MEDIUM);
        request.setExercises(new ArrayList<>());

        Workout workout = Workout.builder().name("Test Workout").duration(Duration.ofMinutes(45)).difficulty(WorkoutDifficulty.MEDIUM).build();
        WorkoutDto dto = new WorkoutDto();
        Mockito.when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
        Mockito.when(workoutMapper.toDto(any(Workout.class))).thenReturn(dto);

        WorkoutDto result = workoutService.create(request);
        Assertions.assertNotNull(result);
        Mockito.verify(workoutRepository).save(any(Workout.class));
    }

    @Test
    void getAll() {
        Workout workout = Workout.builder().id(1L).build();
        Page<Workout> page = new PageImpl<>(List.of(workout));
        Mockito.when(workoutRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(workoutMapper.toDto(any(Workout.class))).thenReturn(new WorkoutDto());

        Page<WorkoutDto> result = workoutService.getAll(0, 10);
        Assertions.assertEquals(1, result.getTotalElements());
    }

    @Test
    void getById_success() {
        Workout workout = Workout.builder().id(2L).build();
        Mockito.when(workoutRepository.findById(2L)).thenReturn(Optional.of(workout));
        Mockito.when(workoutMapper.toDto(any(Workout.class))).thenReturn(new WorkoutDto());
        WorkoutDto result = workoutService.getById(2L);
        Assertions.assertNotNull(result);
    }

    @Test
    void getById_throwsWorkoutNotFound() {
        Mockito.when(workoutRepository.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertThrows(WorkoutNotFoundException.class, () -> workoutService.getById(2L));
    }

    @Test
    void update_success() {
        UpdateWorkoutRequest request = new UpdateWorkoutRequest();
        request.setName("Updated Workout");
        request.setDuration(Duration.ofMinutes(60));
        request.setDifficulty(WorkoutDifficulty.HARD);
        request.setExercises(new ArrayList<>());

        Workout workout = Workout.builder().id(3L).build();
        Mockito.when(workoutRepository.findById(3L)).thenReturn(Optional.of(workout));
        Mockito.when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
        Mockito.when(workoutMapper.toDto(any(Workout.class))).thenReturn(new WorkoutDto());

        WorkoutDto result = workoutService.update(3L, request);
        Assertions.assertNotNull(result);
    }

    @Test
    void update_throwsWorkoutNotFound() {
        UpdateWorkoutRequest request = new UpdateWorkoutRequest();
        Mockito.when(workoutRepository.findById(4L)).thenReturn(Optional.empty());
        Assertions.assertThrows(WorkoutNotFoundException.class, () -> workoutService.update(4L, request));
    }

    @Test
    void delete_success() {
        Mockito.when(workoutRepository.existsById(5L)).thenReturn(true);
        workoutService.delete(5L);
        Mockito.verify(workoutRepository).deleteById(5L);
    }

    @Test
    void delete_notFound() {
        Mockito.when(workoutRepository.existsById(6L)).thenReturn(false);
        Assertions.assertThrows(WorkoutNotFoundException.class, () -> workoutService.delete(6L));
    }
}
