package com.orlovandrei.fit_rest.dto.weight;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeightEntryDto {
    LocalDate date;
    Double value;
}

