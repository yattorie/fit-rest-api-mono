package com.orlovandrei.fit_rest.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {
    EMAIL("Email "),
    INVALID_REFRESH_TOKEN("Invalid refresh token"),
    IS_ALREADY_TAKEN(" is already taken."),
    USERNAME("Username "),
    RECIPE_NOT_FOUND("Recipe not found."),
    ARTICLE_NOT_FOUND("Article not found"),
    IMAGE_UPLOAD_FAILED("Image upload failed"),
    MINIO_BUCKET("MinIO bucket '"),
    MINIO_BUCKET_ERROR_INIT("Error during MinIO bucket initialization."),
    CREATED("' created."),
    EXISTS("' already exists."),
    USER_NOT_FOUND("User not found");

    private final String message;
}
