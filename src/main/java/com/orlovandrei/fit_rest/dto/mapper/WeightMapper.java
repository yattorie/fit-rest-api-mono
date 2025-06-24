package com.orlovandrei.fit_rest.dto.mapper;

import com.orlovandrei.fit_rest.dto.weight.WeightEntryDto;
import com.orlovandrei.fit_rest.entity.weight.WeightEntry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeightMapper {
    WeightEntryDto toDto(WeightEntry entry);
}

