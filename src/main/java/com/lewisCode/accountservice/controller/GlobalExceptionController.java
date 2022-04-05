package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.entity.ExceptionMessage;
import com.lewisCode.accountservice.exeptions.BreachedPasswordException;
import com.lewisCode.accountservice.exeptions.SamePasswordException;
import com.lewisCode.accountservice.exeptions.UserExistException;
import com.lewisCode.accountservice.exeptions.WrongPaymentException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    private final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);
        body.put("Path",request.getContextPath());

        return new ResponseEntity<>(body, headers, status);

    }

    @ExceptionHandler (value = UserExistException.class)
    public ResponseEntity<Object>userAllReadyExist(HttpServletRequest request,
                                                   UserExistException e){
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
    @ExceptionHandler (value = WrongPaymentException.class)
    public ResponseEntity<Object>wrongPaymentException(HttpServletRequest request,
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
