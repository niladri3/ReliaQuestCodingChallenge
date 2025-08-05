package com.reliaquest.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ClientException extends RuntimeException {
    private final HttpStatusCode statusCode;

    public ClientException(HttpStatusCode statusCode, String msg) {
        super(msg);
        this.statusCode = statusCode;
    }
}
