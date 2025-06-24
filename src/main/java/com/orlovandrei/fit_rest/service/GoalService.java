package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.goal.CreateGoalRequest;
import com.orlovandrei.fit_rest.dto.goal.GoalDto;
import com.orlovandrei.fit_rest.dto.goal.UpdateGoalRequest;

import java.util.List;

public interface GoalService {
    GoalDto create(CreateGoalRequest request, String username);
    List<GoalDto> getAllByUser(String username);
    void delete(Long id, String username);
    GoalDto getById(Long id, String username);
    GoalDto update(Long id, UpdateGoalRequest request, String username);

}

