package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.mapper.MealPlanMapper;
import com.orlovandrei.fit_rest.dto.meal.CreateMealEntryRequest;
import com.orlovandrei.fit_rest.dto.meal.CreateMealPlanRequest;
import com.orlovandrei.fit_rest.dto.meal.MealPlanDto;
import com.orlovandrei.fit_rest.entity.MealPlan;
import com.orlovandrei.fit_rest.entity.Recipe;
import com.orlovandrei.fit_rest.entity.User;
import com.orlovandrei.fit_rest.exception.MealPlanNotFoundException;
import com.orlovandrei.fit_rest.exception.RecipeNotFoundException;
import com.orlovandrei.fit_rest.repository.MealPlanRepository;
import com.orlovandrei.fit_rest.repository.RecipeRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MealPlanServiceImplTest {

    @Mock
    private MealPlanRepository mealPlanRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private MealPlanMapper mealPlanMapper;
    @InjectMocks
    private MealPlanServiceImpl mealPlanService;

    @Test
    void getAllByUser() {
        MealPlan plan = MealPlan.builder().id(1L).build();
        Page<MealPlan> page = new PageImpl<>(List.of(plan));
        Mockito.when(mealPlanRepository.findByUserUsername(Mockito.eq("user"), any(Pageable.class))).thenReturn(page);
        Mockito.when(mealPlanMapper.toDto(plan)).thenReturn(new MealPlanDto());
        Page<MealPlanDto> result = mealPlanService.getAllByUser("user", 0, 10);
        Assertions.assertEquals(1, result.getTotalElements());
    }

    @Test
    void getById_success() {
        User user = User.builder().username("user").build();
        MealPlan plan = MealPlan.builder().id(2L).user(user).build();
        Mockito.when(mealPlanRepository.findById(2L)).thenReturn(Optional.of(plan));
        Mockito.when(mealPlanMapper.toDto(plan)).thenReturn(new MealPlanDto());
        MealPlanDto result = mealPlanService.getById(2L, "user");
        Assertions.assertNotNull(result);
    }

    @Test
    void getById_forbidden() {
        User user = User.builder().username("other").build();
        MealPlan plan = MealPlan.builder().id(3L).user(user).build();
        Mockito.when(mealPlanRepository.findById(3L)).thenReturn(Optional.of(plan));
        Assertions.assertThrows(AccessDeniedException.class, () -> mealPlanService.getById(3L, "notowner"));
    }

    @Test
    void getById_throwsMealPlanNotFound() {
        Mockito.when(mealPlanRepository.findById(4L)).thenReturn(Optional.empty());
        Assertions.assertThrows(MealPlanNotFoundException.class, () -> mealPlanService.getById(4L, "user"));
    }

    @Test
    void create_success() {
        CreateMealPlanRequest request = new CreateMealPlanRequest();
        request.setTitle("plan");
        request.setDescription("desc");
        CreateMealEntryRequest entryRequest = new CreateMealEntryRequest();
        entryRequest.setRecipeId(1L);
        request.setMeals(List.of(entryRequest));

        User user = User.builder().username("user").build();
        Recipe recipe = Recipe.builder().id(1L).build();
        MealPlan plan = MealPlan.builder().id(10L).user(user).build();
        MealPlanDto dto = new MealPlanDto();

        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        Mockito.when(mealPlanRepository.save(any(MealPlan.class))).thenReturn(plan);
        Mockito.when(mealPlanMapper.toDto(plan)).thenReturn(dto);

        MealPlanDto result = mealPlanService.create(request, "user");
        Assertions.assertNotNull(result);
    }

    @Test
    void create_profile_throwsUsernameNotFoundException() {
        CreateMealPlanRequest request = new CreateMealPlanRequest();
        Mockito.when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> mealPlanService.create(request, "notfound"));
    }

    @Test
    void create_user_throwsRecipeNotFound(){
        CreateMealPlanRequest request = new CreateMealPlanRequest();
        CreateMealEntryRequest entryRequest = new CreateMealEntryRequest();
        entryRequest.setRecipeId(999L);
        request.setMeals(List.of(entryRequest));
        User user = User.builder().username("user").build();
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(recipeRepository.findById(999L)).thenReturn(Optional.empty());
        Assertions.assertThrows(RecipeNotFoundException.class, () -> mealPlanService.create(request, "user"));
    }

    @Test
    void update_success() {
        CreateMealPlanRequest request = new CreateMealPlanRequest();
        request.setTitle("plan");
        request.setDescription("desc");
        CreateMealEntryRequest entryRequest = new CreateMealEntryRequest();
        entryRequest.setRecipeId(1L);
        request.setMeals(List.of(entryRequest));
        User user = User.builder().username("user").build();
        Recipe recipe = Recipe.builder().id(1L).build();

        MealPlan plan = MealPlan.builder().id(20L).user(user).meals(new ArrayList<>()).build();
        MealPlanDto dto = new MealPlanDto();

        Mockito.when(mealPlanRepository.findById(20L)).thenReturn(Optional.of(plan));
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        Mockito.when(mealPlanRepository.save(plan)).thenReturn(plan);
        Mockito.when(mealPlanMapper.toDto(plan)).thenReturn(dto);

        MealPlanDto result = mealPlanService.update(20L, request, "user");
        Assertions.assertNotNull(result);
    }

    @Test
    void update_forbidden() {
        CreateMealPlanRequest request = new CreateMealPlanRequest();
        User user = User.builder().username("other").build();
        MealPlan plan = MealPlan.builder().id(21L).user(user).build();
        Mockito.when(mealPlanRepository.findById(21L)).thenReturn(Optional.of(plan));
        Assertions.assertThrows(AccessDeniedException.class, () -> mealPlanService.update(21L, request, "notowner"));
    }

    @Test
    void delete_success() {
        User user = User.builder().username("user").build();
        MealPlan plan = MealPlan.builder().id(30L).user(user).build();
        Mockito.when(mealPlanRepository.findById(30L)).thenReturn(Optional.of(plan));
        mealPlanService.delete(30L, "user");
        Mockito.verify(mealPlanRepository).delete(plan);
    }

    @Test
    void delete_forbidden() {
        User user = User.builder().username("other").build();
        MealPlan plan = MealPlan.builder().id(31L).user(user).build();
        Mockito.when(mealPlanRepository.findById(31L)).thenReturn(Optional.of(plan));
        Assertions.assertThrows(AccessDeniedException.class, () -> mealPlanService.delete(31L, "notowner"));
    }
}