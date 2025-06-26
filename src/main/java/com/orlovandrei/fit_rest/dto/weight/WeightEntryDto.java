package com.orlovandrei.fit_rest.dto.weight;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Weight entry data transfer object")
public class WeightEntryDto {
    @Schema(
            description = "Date of the weight measurement (yyyy-MM-dd)",
            example = "2023-05-15"
    )
    LocalDate date;

    @Schema(
            description = "Weight value in kilograms",
            example = "75.5"
    )
    Double value;
}