package com.beelinx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthenticatedException extends Exception{

    public UnAuthenticatedException(String message) {
        super(message);
    }
}
