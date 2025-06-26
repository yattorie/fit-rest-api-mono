package com.orlovandrei.fit_rest.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for creating a new category")
public class CreateCategoryRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be under 255 characters")
    @Schema(
            description = "Name of the category",
            example = "Workout Tips"
    )
    String name;
}