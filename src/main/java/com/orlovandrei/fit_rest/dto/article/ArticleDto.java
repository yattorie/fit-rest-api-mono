package com.orlovandrei.fit_rest.dto.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Article data transfer object")
public class ArticleDto {
    @Schema(
            description = "Unique identifier of the article",
            example = "1"
    )
    Long id;

    @Schema(
            description = "Title of the article",
            example = "10 Tips for Healthy Eating"
    )
    String title;

    @Schema(
            description = "URL of the article's main image",
            example = "https://example.com/images/healthy-eating.jpg"
    )
    String imageUrl;

    @Schema(
            description = "Content of the article",
            example = "Here are 10 tips for maintaining a healthy diet..."
    )
    String content;

    @Schema(
            description = "ID of the category this article belongs to",
            example = "3"
    )
    Long categoryId;

    @Schema(
            description = "Name of the category this article belongs to",
            example = "Nutrition"
    )
    String categoryName;

    @Schema(
            description = "Username of the article's author",
            example = "health_expert"
    )
    String authorUsername;

    @Schema(
            description = "Timestamp when the article was created",
            example = "2023-05-15T14:30:00"
    )
    LocalDateTime createdAt;
}

