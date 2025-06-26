package com.orlovandrei.fit_rest.dto.weight;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for adding a new weight entry")
public class AddWeightEntryRequest {
    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    @Schema(
            description = "Date of the weight measurement (yyyy-MM-dd)",
            example = "2023-05-15"
    )
    LocalDate date;

    @NotNull(message = "Weight value is required")
    @Min(value = 30, message = "Weight must be at least 30 kg")
    @Max(value = 500, message = "Weight cannot exceed 500 kg")
    @Schema(
            description = "Weight value in kilograms",
            example = "75.5"
    )
    Double value;
}