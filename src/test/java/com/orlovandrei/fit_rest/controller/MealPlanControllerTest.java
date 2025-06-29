package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.meal.CreateMealPlanRequest;
import com.orlovandrei.fit_rest.dto.meal.MealPlanDto;
import com.orlovandrei.fit_rest.service.MealPlanService;
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
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class MealPlanControllerTest {

    @Mock
    private MealPlanService mealPlanService;

    @InjectMocks
    private MealPlanController mealPlanController;

    @Mock
    private UserDetails userDetails;

    @Test
    void getAll_success() {
        MealPlanDto dto = new MealPlanDto();
        Page<MealPlanDto> page = new PageImpl<>(List.of(dto));
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(mealPlanService.getAllByUser("user", 0, 10)).thenReturn(page);

        ResponseEntity<Page<MealPlanDto>> result = mealPlanController.getAll(userDetails, 0, 10);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().getTotalElements());
    }

    @Test
    void getById_success() {
        MealPlanDto dto = new MealPlanDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(mealPlanService.getById(1L, "user")).thenReturn(dto);

        ResponseEntity<MealPlanDto> result = mealPlanController.getById(1L, userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void create_success() {
        CreateMealPlanRequest req = new CreateMealPlanRequest();
        MealPlanDto dto = new MealPlanDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(mealPlanService.create(req, "user")).thenReturn(dto);

        ResponseEntity<MealPlanDto> result = mealPlanController.create(req, userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void update_success() {
        CreateMealPlanRequest req = new CreateMealPlanRequest();
        MealPlanDto dto = new MealPlanDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(mealPlanService.update(2L, req, "user")).thenReturn(dto);

        ResponseEntity<MealPlanDto> result = mealPlanController.update(2L, req, userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void delete_success() {
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.doNothing().when(mealPlanService).delete(1L, "user");

        ResponseEntity<Void> result = mealPlanController.delete(1L, userDetails);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        Mockito.verify(mealPlanService).delete(1L, "user");
    }
}