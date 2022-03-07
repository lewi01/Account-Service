package com.lewisCode.accountservice.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User Exist!")
public class UserExistException extends RuntimeException{

}
