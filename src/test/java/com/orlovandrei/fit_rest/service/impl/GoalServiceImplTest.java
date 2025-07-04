package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.goal.CreateGoalRequest;
import com.orlovandrei.fit_rest.dto.goal.GoalDto;
import com.orlovandrei.fit_rest.dto.goal.UpdateGoalRequest;
import com.orlovandrei.fit_rest.dto.mapper.GoalMapper;
import com.orlovandrei.fit_rest.entity.Goal;
import com.orlovandrei.fit_rest.entity.enums.GoalType;
import com.orlovandrei.fit_rest.entity.User;
import com.orlovandrei.fit_rest.repository.GoalRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GoalMapper goalMapper;

    @InjectMocks
    private GoalServiceImpl goalService;

    @Test
    void create_success() {
        CreateGoalRequest request = new CreateGoalRequest();
        request.setDescription("desc");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusDays(10));
        request.setType(GoalType.WEIGHT_LOSS);

        User user = User.builder().username("user").build();
        Goal goal = Goal.builder().description("desc").user(user).build();
        GoalDto dto = new GoalDto();

        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(goalRepository.save(any(Goal.class))).thenReturn(goal);
        Mockito.when(goalMapper.toDto(any(Goal.class))).thenReturn(dto);

        GoalDto result = goalService.create(request, "user");

        Assertions.assertNotNull(result);
        Mockito.verify(goalRepository).save(any(Goal.class));
    }

    @Test
    void create_throwsUsernameNotFound() {
        CreateGoalRequest request = new CreateGoalRequest();
        Mockito.when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> goalService.create(request, "notfound"));
    }

    @Test
    void getAllByUser() {
        Goal goal = Goal.builder().id(1L).build();
        Mockito.when(goalRepository.findByUserUsername("user")).thenReturn(List.of(goal));
        Mockito.when(goalMapper.toDto(any(Goal.class))).thenReturn(new GoalDto());
        List<GoalDto> result = goalService.getAllByUser("user");
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void delete_success() {
        User user = User.builder().username("user").build();
        Goal goal = Goal.builder().id(1L).user(user).build();
        Mockito.when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));
        goalService.delete(1L, "user");
        Mockito.verify(goalRepository).delete(goal);
    }

    @Test
    void delete_forbidden() {
        User user = User.builder().username("other").build();
        Goal goal = Goal.builder().id(2L).user(user).build();
        Mockito.when(goalRepository.findById(2L)).thenReturn(Optional.of(goal));
        Assertions.assertThrows(AccessDeniedException.class, () -> goalService.delete(2L, "notowner"));
    }

    @Test
    void getById_success() {
        User user = User.builder().username("user").build();
        Goal goal = Goal.builder().id(3L).user(user).build();
        Mockito.when(goalRepository.findById(3L)).thenReturn(Optional.of(goal));
        Mockito.when(goalMapper.toDto(any(Goal.class))).thenReturn(new GoalDto());
        GoalDto result = goalService.getById(3L, "user");
        Assertions.assertNotNull(result);
    }

    @Test
    void getById_forbidden() {
        User user = User.builder().username("other").build();
        Goal goal = Goal.builder().id(4L).user(user).build();
        Mockito.when(goalRepository.findById(4L)).thenReturn(Optional.of(goal));
        Assertions.assertThrows(AccessDeniedException.class, () -> goalService.getById(4L, "notowner"));
    }

    @Test
    void update_success() {
        User user = User.builder().username("user").build();
        Goal goal = Goal.builder().id(5L).user(user).build();
        UpdateGoalRequest request = new UpdateGoalRequest();
        request.setDescription("new goal");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusDays(5));
        request.setType(GoalType.ENDURANCE);

        Mockito.when(goalRepository.findById(5L)).thenReturn(Optional.of(goal));
        Mockito.when(goalRepository.save(any(Goal.class))).thenReturn(goal);
        Mockito.when(goalMapper.toDto(any(Goal.class))).thenReturn(new GoalDto());

        GoalDto result = goalService.update(5L, request, "user");
        Assertions.assertNotNull(result);
    }

    @Test
    void update_forbidden() {
        User user = User.builder().username("other").build();
        Goal goal = Goal.builder().id(6L).user(user).build();
        UpdateGoalRequest request = new UpdateGoalRequest();
        request.setDescription("desc");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusDays(2));
        request.setType(GoalType.HABIT);
        Mockito.when(goalRepository.findById(6L)).thenReturn(Optional.of(goal));
        Assertions.assertThrows(AccessDeniedException.class, () -> goalService.update(6L, request, "notowner"));
    }
}