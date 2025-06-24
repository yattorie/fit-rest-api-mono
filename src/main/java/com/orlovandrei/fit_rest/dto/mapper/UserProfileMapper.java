package com.orlovandrei.fit_rest.dto.mapper;

import com.orlovandrei.fit_rest.dto.user.UserProfileDto;
import com.orlovandrei.fit_rest.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(target = "age", expression = "java(calculateAge(user.getBirthDate()))")
    UserProfileDto toProfileDto(User user);

    default Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) return null;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}

