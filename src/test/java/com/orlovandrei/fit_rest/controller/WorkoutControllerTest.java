package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.workout.CreateWorkoutRequest;
import com.orlovandrei.fit_rest.dto.workout.UpdateWorkoutRequest;
import com.orlovandrei.fit_rest.dto.workout.WorkoutDto;
import com.orlovandrei.fit_rest.service.WorkoutService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class WorkoutControllerTest {

    @Mock
    private WorkoutService workoutService;

    @InjectMocks
    private WorkoutController workoutController;

    @Test
    void create_success() {
        CreateWorkoutRequest req = new CreateWorkoutRequest();
        WorkoutDto dto = new WorkoutDto();
        Mockito.when(workoutService.create(req)).thenReturn(dto);

        ResponseEntity<WorkoutDto> result = workoutController.create(req);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void getAll_success() {
        WorkoutDto dto = new WorkoutDto();
        Page<WorkoutDto> page = new PageImpl<>(List.of(dto));
        Mockito.when(workoutService.getAll(0, 10)).thenReturn(page);

        ResponseEntity<Page<WorkoutDto>> result = workoutController.getAll(0, 10);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().getTotalElements());
    }

    @Test
    void getById_success() {
        WorkoutDto dto = new WorkoutDto();
        Mockito.when(workoutService.getById(1L)).thenReturn(dto);

        ResponseEntity<WorkoutDto> result = workoutController.getById(1L);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void update_success() {
        UpdateWorkoutRequest req = new UpdateWorkoutRequest();
        WorkoutDto dto = new WorkoutDto();
        Mockito.when(workoutService.update(1L, req)).thenReturn(dto);

        ResponseEntity<WorkoutDto> result = workoutController.update(1L, req);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void delete_success() {
        Mockito.doNothing().when(workoutService).delete(1L);

        ResponseEntity<Void> result = workoutController.delete(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        Mockito.verify(workoutService).delete(1L);
    }
}