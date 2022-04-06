package com.lewisCode.accountservice.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error!")
public class WrongPaymentException extends RuntimeException{

    public WrongPaymentException(String message) {
        super(message);
    }
}
