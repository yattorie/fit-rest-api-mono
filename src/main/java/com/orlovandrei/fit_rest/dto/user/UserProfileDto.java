package com.orlovandrei.fit_rest.dto.user;

import com.orlovandrei.fit_rest.entity.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "User profile data transfer object")
public class UserProfileDto {
    @Schema(
            description = "User's username",
            example = "test_user"
    )
    String username;

    @Schema(
            description = "User's email address",
            example = "test.user@example.com"
    )
    String email;

    @Schema(
            description = "User's weight in kilograms",
            example = "75.5"
    )
    Double weight;

    @Schema(
            description = "User's height in centimeters",
            example = "180.0"
    )
    Double height;

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

    @Schema(
            description = "User's age (calculated from birth date)",
            example = "33"
    )
    Integer age;
}