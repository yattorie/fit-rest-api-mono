package com.orlovandrei.fit_rest.exception;

public class GoalNotFoundException extends RuntimeException{
    public GoalNotFoundException(String message) {
        super(message);
    }
}
