package com.orlovandrei.fit_rest.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCommentRequest {
    @NotBlank(message = "Content is required")
    @Size(max = 1000, message = "Content must be under 1000 characters")
    String content;
}

