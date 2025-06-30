package com.orlovandrei.fit_rest.exception;

import com.orlovandrei.fit_rest.util.LoggerUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleCategoryAlreadyExistsException(CategoryAlreadyExistsException e) {
        LoggerUtil.logError("Category already exists: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(WeightEntryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleWeightEntryAlreadyExists(WeightEntryAlreadyExistsException e) {
        LoggerUtil.logError("Weight entry already exists: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(MealPlanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleMealPlanNotFoundException(MealPlanNotFoundException e) {
        LoggerUtil.logError("Meal plan not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IncompleteProfileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIncompleteProfileException(IncompleteProfileException e) {
        LoggerUtil.logError("Incomplete profile: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleArticleNotFoundException(ArticleNotFoundException e) {
        LoggerUtil.logError("Article not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleCommentNotFoundException(CommentNotFoundException e) {
        LoggerUtil.logError("Comment not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleCategoryNotFoundException(CategoryNotFoundException e) {
        LoggerUtil.logError("Category not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(GoalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleGoalNotFoundExceptionException(GoalNotFoundException e) {
        LoggerUtil.logError("Goal not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleRecipeNotFoundExceptionException(RecipeNotFoundException e) {
        LoggerUtil.logError("Recipe not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(WorkoutNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleWorkoutNotFoundExceptionException(WorkoutNotFoundException e) {
        LoggerUtil.logError("Workout not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleUserAlreadyExists(UserAlreadyExistsException e) {
        LoggerUtil.logError("User already exists: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        LoggerUtil.logError("Email already exists: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleUserNotFoundException(UserNotFoundException e) {
        LoggerUtil.logError("User not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalState(IllegalStateException e) {
        LoggerUtil.logError("Illegal state: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleResourceNotFoundException(MethodArgumentNotValidException e) {
        LoggerUtil.logError("Validation failed: " + e.getMessage(), e);
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(fieldErrors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolation(ConstraintViolationException e) {
        LoggerUtil.logError("Constraint violation: " + e.getMessage(), e);
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        exceptionBody.setErrors(e.getConstraintViolations().stream().collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                violation -> violation.getMessage())));
        return exceptionBody;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleAuthenticationException(AuthenticationException e) {
        LoggerUtil.logError("Authentication failed: " + e.getMessage(), e);
        return new ExceptionBody("Authentication failed");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDeniedException(AccessDeniedException e) {
        LoggerUtil.logError("Access denied: " + e.getMessage(), e);
        return new ExceptionBody("Access denied: You do not have permission to perform this action");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(Exception e) {
        LoggerUtil.logError("Internal server error", e);
        return new ExceptionBody("Internal error");
    }


}
