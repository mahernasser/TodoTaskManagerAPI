package com.maher.exceptions.base;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatusCode();

}
