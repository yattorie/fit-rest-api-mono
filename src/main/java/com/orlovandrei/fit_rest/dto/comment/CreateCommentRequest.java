package com.orlovandrei.fit_rest.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for creating a new comment")
public class CreateCommentRequest {
    @NotBlank(message = "Content is required")
    @Size(max = 1000, message = "Content must be under 1000 characters")
    @Schema(
            description = "Content of the comment",
            example = "I found this article very informative!"
    )
    String content;
}