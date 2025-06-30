package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.mapper.UserProfileMapper;
import com.orlovandrei.fit_rest.dto.user.UpdateUserProfileRequest;
import com.orlovandrei.fit_rest.dto.user.UserProfileDto;
import com.orlovandrei.fit_rest.entity.goal.Goal;
import com.orlovandrei.fit_rest.entity.user.User;
import com.orlovandrei.fit_rest.exception.IncompleteProfileException;
import com.orlovandrei.fit_rest.repository.GoalRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
import com.orlovandrei.fit_rest.service.UserProfileService;
import com.orlovandrei.fit_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileMapper profileMapper;
    private final GoalRepository goalRepository;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage() + username));
        return profileMapper.toProfileDto(user);
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(UpdateUserProfileRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage() + username));

        user.setWeight(request.getWeight());
        user.setHeight(request.getHeight());
        user.setBirthDate(request.getBirthDate());
        user.setGender(request.getGender());

        userRepository.save(user);
        return profileMapper.toProfileDto(user);
    }

    @Override
    public int calculateCalorieNorm(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage() + username));

        if (user.getWeight() == null || user.getHeight() == null || user.getBirthDate() == null || user.getGender() == null) {
            throw new IncompleteProfileException(Messages.PROFILE_INCOMPLETE.getMessage());
        }

        int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();
        double bmr;

        switch (user.getGender()) {
            case MALE -> bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * age + 5;
            case FEMALE -> bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * age - 161;
            default -> bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * age;
        }

        Goal goal = goalRepository.findByUserUsername(user.getUsername()).stream()
                .max(Comparator.comparing(Goal::getEndDate))
                .orElse(null);

        double multiplier = 1.0;

        if (goal != null) {
            switch (goal.getType()) {
                case WEIGHT_LOSS -> multiplier = 0.85;
                case MUSCLE_GAIN -> multiplier = 1.15;
                case ENDURANCE, HABIT -> multiplier = 1.0;
            }
        }

        return (int) Math.round(bmr * multiplier);
    }
}
