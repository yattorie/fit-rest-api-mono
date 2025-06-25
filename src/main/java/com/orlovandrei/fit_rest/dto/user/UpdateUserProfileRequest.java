package com.orlovandrei.fit_rest.dto.user;

import com.orlovandrei.fit_rest.entity.user.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserProfileRequest {
    @Min(value = 30, message = "Weight must be at least 30 kg")
    @Max(value = 500, message = "Weight cannot exceed 500 kg")
    Double weight;

    @Min(value = 50, message = "Height must be at least 50 cm")
    @Max(value = 300, message = "Height cannot exceed 300 cm")
    Double height;

    @Past(message = "Birth date must be in the past")
    LocalDate birthDate;

    Gender gender;
}

