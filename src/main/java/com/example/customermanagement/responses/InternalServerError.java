package com.example.customermanagement.responses;

import org.springframework.http.HttpStatus;

public class InternalServerError {
    HttpStatus httpStatus;
    String message;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
