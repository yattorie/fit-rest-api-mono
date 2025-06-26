package com.orlovandrei.fit_rest.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Comment data transfer object")
public class CommentDto {
    @Schema(
            description = "Unique identifier of the comment",
            example = "1"
    )
    Long id;

    @Schema(
            description = "Content of the comment",
            example = "This article was really helpful, thanks for sharing!"
    )
    String content;

    @Schema(
            description = "Username of the comment author",
            example = "example_user"
    )
    String authorUsername;

    @Schema(
            description = "Timestamp when the comment was created",
            example = "2023-05-15T14:30:00"
    )
    LocalDateTime createdAt;
}