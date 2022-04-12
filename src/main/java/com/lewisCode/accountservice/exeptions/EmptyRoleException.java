package com.lewisCode.accountservice.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The user must have at least one role!")
public class EmptyRoleException extends RuntimeException {
    public EmptyRoleException(String message) {
        super(message);
    }
}
