package com.lewisCode.accountservice.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User Exist!")
public class UserExistException extends RuntimeException{

    public UserExistException(String message) {
        super(message);
    }
}
