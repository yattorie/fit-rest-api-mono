package com.orlovandrei.fit_rest.exception;

public class MealPlanNotFoundException extends RuntimeException {
    public MealPlanNotFoundException(String message) {
        super(message);
    }
}
