package com.lewisCode.accountservice.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The user does not have a role!")
public class UserHasNotRoleSuchException extends RuntimeException {
    public UserHasNotRoleSuchException(String message) {
        super(message);
    }
}
