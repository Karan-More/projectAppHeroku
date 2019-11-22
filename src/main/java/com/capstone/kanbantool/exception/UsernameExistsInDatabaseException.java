package com.capstone.kanbantool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameExistsInDatabaseException extends RuntimeException{

    public UsernameExistsInDatabaseException(String message) {
        super(message);
    }
}
