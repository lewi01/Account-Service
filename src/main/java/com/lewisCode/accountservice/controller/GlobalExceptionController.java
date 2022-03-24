package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.entity.ExceptionMessage;
import com.lewisCode.accountservice.exeptions.BreachedPasswordException;
import com.lewisCode.accountservice.exeptions.SamePasswordException;
import com.lewisCode.accountservice.exeptions.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionController {

    private final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

    @ExceptionHandler (value = UserExistException.class)
    public ResponseEntity<Object>userAllReadyExist(HttpServletRequest request, UserExistException e){
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
    @ExceptionHandler (value = BreachedPasswordException.class)
    public ResponseEntity<Object>breachedPasswordException(HttpServletRequest request,
                                                           BreachedPasswordException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
    @ExceptionHandler (value = SamePasswordException.class)
    public ResponseEntity<Object>samePasswordException(HttpServletRequest request,
                                                       SamePasswordException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
}
