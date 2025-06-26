package com.orlovandrei.fit_rest.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for updating an existing comment")
public class UpdateCommentRequest {
    @NotBlank(message = "Content is required")
    @Size(max = 1000, message = "Content must be under 1000 characters")
    @Schema(
            description = "Updated content of the comment",
            example = "After re-reading, I have additional insights to share..."
    )
    String content;
}