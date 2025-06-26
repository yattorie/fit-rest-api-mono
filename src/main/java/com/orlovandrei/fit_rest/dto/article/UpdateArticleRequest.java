package com.orlovandrei.fit_rest.dto.article;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for updating an existing article")
public class UpdateArticleRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be under 255 characters")
    @Schema(
            description = "Title of the article",
            example = "10 Essential Tips for Healthy Eating"
    )
    String title;

    @NotBlank(message = "Content is required")
    @Schema(
            description = "Content of the article",
            example = "Here are 10 essential tips for maintaining a healthy diet..."
    )
    String content;

    @NotNull(message = "Category ID is required")
    @Schema(
            description = "ID of the category this article belongs to",
            example = "3"
    )
    Long categoryId;
}

