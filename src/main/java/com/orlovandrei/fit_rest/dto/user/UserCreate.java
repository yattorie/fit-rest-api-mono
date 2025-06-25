package com.orlovandrei.fit_rest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserCreate {
    @NotBlank(message = "Username must not be blank")
    @Size(min = 6, max = 255, message = "Username must be between 6 and 255 characters.")
    String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must be smaller than 255 characters.")
    String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
}
