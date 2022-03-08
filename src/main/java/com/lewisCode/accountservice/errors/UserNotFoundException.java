package com.lewisCode.accountservice.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "user doesn't exist")
public class UserNotFoundException extends RuntimeException{
}
