package com.orlovandrei.fit_rest.service.impl;

import com.orlovandrei.fit_rest.dto.goal.CreateGoalRequest;
import com.orlovandrei.fit_rest.dto.goal.GoalDto;
import com.orlovandrei.fit_rest.dto.goal.UpdateGoalRequest;
import com.orlovandrei.fit_rest.dto.mapper.GoalMapper;
import com.orlovandrei.fit_rest.entity.goal.Goal;
import com.orlovandrei.fit_rest.entity.user.User;
import com.orlovandrei.fit_rest.exception.GoalNotFoundException;
import com.orlovandrei.fit_rest.repository.GoalRepository;
import com.orlovandrei.fit_rest.repository.UserRepository;
import com.orlovandrei.fit_rest.service.GoalService;
import com.orlovandrei.fit_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final GoalMapper goalMapper;

    @Override
    @Transactional
    public GoalDto create(CreateGoalRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage()));

        Goal goal = Goal.builder()
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .type(request.getType())
                .user(user)
                .build();

        goalRepository.save(goal);
        return goalMapper.toDto(goal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalDto> getAllByUser(String username) {
        return goalRepository.findByUserUsername(username).stream()
                .map(goalMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(Long id, String username) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new GoalNotFoundException(Messages.GOAL_NOT_FOUND_BY_ID.getMessage() +id));
        if (!goal.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException(Messages.DELETE_ONLY_YOUR_GOALS.getMessage());
        }
        goalRepository.delete(goal);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalDto getById(Long id, String username) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new GoalNotFoundException(Messages.GOAL_NOT_FOUND_BY_ID.getMessage() +id));

        if (!goal.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException(Messages.GET_ONLY_YOUR_GOALS.getMessage());
        }

        return goalMapper.toDto(goal);
    }

    @Override
    @Transactional
    public GoalDto update(Long id, UpdateGoalRequest request, String username) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new GoalNotFoundException(Messages.GOAL_NOT_FOUND_BY_ID.getMessage() +id));

        if (!goal.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException(Messages.CHANGE_ONLY_YOUR_GOALS.getMessage());
        }

        goal.setDescription(request.getDescription());
        goal.setStartDate(request.getStartDate());
        goal.setEndDate(request.getEndDate());
        goal.setType(request.getType());

        return goalMapper.toDto(goalRepository.save(goal));
    }

}

