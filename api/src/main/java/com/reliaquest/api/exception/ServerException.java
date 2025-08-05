package com.reliaquest.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ServerException extends RuntimeException{

    private final HttpStatusCode statusCode;

    public ServerException(HttpStatusCode statusCode, String msg) {
        super(msg);
        this.statusCode = statusCode;
    }
}
