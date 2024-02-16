package com.maher.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(message);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

}
