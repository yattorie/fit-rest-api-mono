package com.orlovandrei.fit_rest.dto.user;

import com.orlovandrei.fit_rest.entity.user.Gender;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileDto {
    String username;
    String email;
    Double weight;
    Double height;
    LocalDate birthDate;
    Gender gender;
    Integer age;
}

