package com.orlovandrei.fit_rest.dto.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Response indicating the success of an operation")
public class SuccessResponse {
    @Schema(
            description = "Message describing the outcome of the operation",
            example = "Registration successful"
    )
    String message;
}

