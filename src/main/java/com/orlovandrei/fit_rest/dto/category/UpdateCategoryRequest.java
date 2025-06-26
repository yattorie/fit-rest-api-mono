package com.orlovandrei.fit_rest.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for updating an existing category")
public class UpdateCategoryRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be under 255 characters")
    @Schema(
            description = "Updated name of the category",
            example = "Fitness Advice"
    )
    String name;
}