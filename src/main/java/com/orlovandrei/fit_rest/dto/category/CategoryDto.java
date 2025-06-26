package com.orlovandrei.fit_rest.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Category data transfer object")
public class CategoryDto {
    @Schema(
            description = "Unique identifier of the category",
            example = "1"
    )
    Long id;
    @Schema(
            description = "Name of the category",
            example = "Nutrition"
    )
    String name;
}

