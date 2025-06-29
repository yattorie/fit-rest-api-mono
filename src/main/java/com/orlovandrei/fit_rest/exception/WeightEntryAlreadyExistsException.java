package com.orlovandrei.fit_rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WeightEntryAlreadyExistsException extends RuntimeException {
    public WeightEntryAlreadyExistsException(String message) {
        super(message);
    }
}