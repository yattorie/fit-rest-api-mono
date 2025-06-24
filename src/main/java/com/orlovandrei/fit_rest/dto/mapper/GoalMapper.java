package com.orlovandrei.fit_rest.dto.mapper;

import com.orlovandrei.fit_rest.dto.goal.GoalDto;
import com.orlovandrei.fit_rest.entity.goal.Goal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    GoalDto toDto(Goal goal);
}

