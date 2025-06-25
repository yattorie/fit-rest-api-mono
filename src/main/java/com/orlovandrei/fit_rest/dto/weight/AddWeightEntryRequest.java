package com.orlovandrei.fit_rest.dto.weight;

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
public class AddWeightEntryRequest {
    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    LocalDate date;

    @NotNull(message = "Weight value is required")
    @Min(value = 30, message = "Weight must be at least 30 kg")
    @Max(value = 500, message = "Weight cannot exceed 500 kg")
    Double value;
}

