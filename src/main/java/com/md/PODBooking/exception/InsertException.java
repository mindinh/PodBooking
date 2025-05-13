package com.md.PODBooking.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsertException extends RuntimeException {

    private String message;

    public InsertException(String message) {
        super(message);
    }
}
