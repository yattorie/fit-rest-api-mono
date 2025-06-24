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
    RECIPE_NOT_FOUND_BY_ID("Recipe not found with id: "),
    ARTICLE_NOT_FOUND_BY_ID("Article not found with id: "),
    IMAGE_UPLOAD_FAILED("Image upload failed"),
    MINIO_BUCKET("MinIO bucket '"),
    MINIO_BUCKET_ERROR_INIT("Error during MinIO bucket initialization."),
    CREATED("' created."),
    EXISTS("' already exists."),
    CATEGORY_NOT_FOUND_BY_ID("Category not found with id: "),
    EDIT_ONLY_YOUR_COMMENT("You can only edit your own comment"),
    DELETE_ONLY_YOUR_COMMENT("You can only delete your own comment"),
    WORKOUT_NOT_FOUND_BY_ID("Workout not found with id: "),
    GOAL_NOT_FOUND_BY_ID("Goal not found with id: "),
    COMMENT_NOT_FOUND_BY_ID("Comment not found with id: "),
    USER_NOT_FOUND_EMAIL("User not found with email: "),
    USER_NOT_FOUND_BY_ID("User not found with id:"),
    USER_NOT_FOUND_USERNAME("User not found with username: "),
    MEAL_PLAN_NOT_FOUND_BY_ID("Meal Plan not found with id: "),
    CHANGE_ONLY_YOUR_GOALS("You can only change your goals."),
    GET_ONLY_YOUR_GOALS("You can only get your goals."),
    DELETE_ONLY_YOUR_GOALS("You can only get your goals."),
    USER_NOT_FOUND("User not found"),
    PROFILE_INCOMPLETE("Profile data is incomplete. Please ensure weight, height, birthdate, and gender are provided."),
    MEAL_PLAN_ACCESS_DENIED("You can only manage your own meal plans"),
    MEAL_PLAN_OPERATION_DENIED("You can only manage your own meal plans");

    private final String message;
}
