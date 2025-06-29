package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.goal.CreateGoalRequest;
import com.orlovandrei.fit_rest.dto.goal.GoalDto;
import com.orlovandrei.fit_rest.dto.goal.UpdateGoalRequest;
import com.orlovandrei.fit_rest.service.GoalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class GoalControllerTest {

    @Mock
    private GoalService goalService;

    @InjectMocks
    private GoalController goalController;

    @Mock
    private UserDetails userDetails;

    @Test
    void create_success() {
        CreateGoalRequest request = new CreateGoalRequest();
        GoalDto dto = new GoalDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(goalService.create(request, "user")).thenReturn(dto);

        ResponseEntity<GoalDto> result = goalController.create(request, userDetails);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void getAll_success() {
        GoalDto dto = new GoalDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(goalService.getAllByUser("user")).thenReturn(List.of(dto));

        ResponseEntity<List<GoalDto>> result = goalController.getAll(userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().size());
    }

    @Test
    void delete_success() {
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.doNothing().when(goalService).delete(1L, "user");

        ResponseEntity<?> result = goalController.delete(1L, userDetails);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        Mockito.verify(goalService).delete(1L, "user");
    }

    @Test
    void getById_success() {
        GoalDto dto = new GoalDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(goalService.getById(2L, "user")).thenReturn(dto);

        ResponseEntity<GoalDto> result = goalController.getById(2L, userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void update_success() {
        UpdateGoalRequest request = new UpdateGoalRequest();
        GoalDto dto = new GoalDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(goalService.update(3L, request, "user")).thenReturn(dto);

        ResponseEntity<GoalDto> result = goalController.update(3L, request, userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }
}