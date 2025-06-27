package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.mapper.UserProfileMapper;
import com.orlovandrei.fit_rest.dto.user.UpdateUserProfileRequest;
import com.orlovandrei.fit_rest.dto.user.UserProfileDto;
import com.orlovandrei.fit_rest.entity.goal.Goal;
import com.orlovandrei.fit_rest.entity.goal.GoalType;
import com.orlovandrei.fit_rest.entity.user.Gender;
import com.orlovandrei.fit_rest.entity.user.User;
import com.orlovandrei.fit_rest.exception.IncompleteProfileException;
import com.orlovandrei.fit_rest.repository.GoalRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserProfileMapper profileMapper;
    @Mock
    private GoalRepository goalRepository;
    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Test
    void getProfile_success() {
        User user = User.builder().username("user").build();
        UserProfileDto dto = new UserProfileDto();
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(profileMapper.toProfileDto(user)).thenReturn(dto);

        UserProfileDto result = userProfileService.getProfile("user");
        Assertions.assertNotNull(result);
    }

    @Test
    void getProfile_userNotFound() {
        Mockito.when(userRepository.findByUsername("u")).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userProfileService.getProfile("u"));
    }

    @Test
    void updateProfile_success() {
        UpdateUserProfileRequest req = new UpdateUserProfileRequest();
        req.setWeight(80.0);
        req.setHeight(180.0);
        req.setBirthDate(LocalDate.of(2000, 1, 1));
        req.setGender(Gender.MALE);

        User user = User.builder().username("user").build();
        UserProfileDto dto = new UserProfileDto();

        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(profileMapper.toProfileDto(user)).thenReturn(dto);

        UserProfileDto result = userProfileService.updateProfile(req, "user");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto, result);
    }

    @Test
    void calculateCalorieNorm_success_male() {
        User user = User.builder()
                .username("user")
                .weight(80.0)
                .height(180.0)
                .birthDate(LocalDate.now().minusYears(30))
                .gender(Gender.MALE)
                .build();

        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(goalRepository.findByUserUsername("user")).thenReturn(List.of());

        int norm = userProfileService.calculateCalorieNorm("user");
        Assertions.assertTrue(norm > 0);
    }

    @Test
    void calculateCalorieNorm_throwsIncompleteProfile() {
        User user = User.builder()
                .username("user")
                .weight(null)
                .height(180.0)
                .birthDate(LocalDate.now().minusYears(30))
                .gender(Gender.MALE)
                .build();

        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Assertions.assertThrows(IncompleteProfileException.class, () -> userProfileService.calculateCalorieNorm("user"));
    }

    @Test
    void calculateCalorieNorm_withWeightLossGoal() {
        User user = User.builder()
                .username("user")
                .weight(80.0)
                .height(180.0)
                .birthDate(LocalDate.now().minusYears(30))
                .gender(Gender.MALE)
                .build();
        Goal goal = Goal.builder().type(GoalType.WEIGHT_LOSS).endDate(LocalDate.now().plusDays(10)).build();

        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(goalRepository.findByUserUsername("user")).thenReturn(List.of(goal));

        int norm = userProfileService.calculateCalorieNorm("user");
        Assertions.assertTrue(norm > 0);
    }
}