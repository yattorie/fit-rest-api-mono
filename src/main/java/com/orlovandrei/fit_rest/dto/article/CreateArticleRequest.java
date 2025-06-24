package com.orlovandrei.fit_rest.dto.article;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateArticleRequest {
    String title;
    Long categoryId;
    String content;
}

