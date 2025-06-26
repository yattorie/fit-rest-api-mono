package com.orlovandrei.fit_rest.dto.user;

import com.orlovandrei.fit_rest.entity.user.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for updating user profile information")
public class UpdateUserProfileRequest {
    @Min(value = 30, message = "Weight must be at least 30 kg")
    @Max(value = 500, message = "Weight cannot exceed 500 kg")
    @Schema(
            description = "User's weight in kilograms",
            example = "75.5"
    )
    Double weight;

    @Min(value = 50, message = "Height must be at least 50 cm")
    @Max(value = 300, message = "Height cannot exceed 300 cm")
    @Schema(
            description = "User's height in centimeters",
            example = "180.0"
    )
    Double height;

    @Past(message = "Birth date must be in the past")
    @Schema(
            description = "User's birth date (yyyy-MM-dd)",
            example = "1990-05-15"
    )
    LocalDate birthDate;

    @Schema(
            description = "User's gender",
            example = "MALE"
    )
    Gender gender;
}