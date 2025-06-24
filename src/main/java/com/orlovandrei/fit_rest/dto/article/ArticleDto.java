package com.orlovandrei.fit_rest.dto.article;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleDto {
    Long id;
    String title;
    String imageUrl;
    String content;
    Long categoryId;
    String categoryName;
    String authorUsername;
    LocalDateTime createdAt;
}

