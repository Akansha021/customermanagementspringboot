package com.example.customermanagement.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ResponseStatus extends RuntimeException{
    HttpStatus httpStatus;
    HttpStatusCode httpStatusCode;
    String message;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
