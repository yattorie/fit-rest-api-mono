package com.orlovandrei.fit_rest.exception;

public class WorkoutNotFoundException extends RuntimeException{
    public WorkoutNotFoundException(String message) {
        super(message);
    }
}

