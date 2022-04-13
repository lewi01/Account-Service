package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.entity.ExceptionMessage;
import com.lewisCode.accountservice.exeptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    private final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    private final HttpStatus notFound  = HttpStatus.NOT_FOUND;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setTimestamp(LocalDateTime.now());
        exceptionMessage.setError(badRequest);
        exceptionMessage.setStatus(badRequest.value());
        exceptionMessage.setMessage(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
        exceptionMessage.setPath(((ServletWebRequest) request).getRequest().getServletPath());

        return new ResponseEntity<>(exceptionMessage,badRequest);
    }

    @ExceptionHandler (value = RoleNotFoundException .class)
    public ResponseEntity<Object>roleNotFoundException (HttpServletRequest request,
                                                        RoleNotFoundException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                notFound.value(),
                notFound,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, notFound);
    }

    @ExceptionHandler (value = UserNotFoundException .class)
    public ResponseEntity<Object>userNotFoundException (HttpServletRequest request,
                                                        UserNotFoundException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                notFound.value(),
                notFound,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, notFound);
    }
    @ExceptionHandler (value = UserExistException .class)
    public ResponseEntity<Object>userExistException(HttpServletRequest request,
                                                        UserExistException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
    @ExceptionHandler (value = NotSupportedRoleOperationException .class)
    public ResponseEntity<Object>notSupportedRoleOperationException(HttpServletRequest request,
                                                                    NotSupportedRoleOperationException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }

    @ExceptionHandler (value = EmptyRoleException .class)
    public ResponseEntity<Object>emptyRoleException(HttpServletRequest request,
                                                    EmptyRoleException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
    @ExceptionHandler (value = WrongPaymentException .class)
    public ResponseEntity<Object>wrongPaymentException(HttpServletRequest request,
                                                       WrongPaymentException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }

    @ExceptionHandler (value = BlockAdministratorException .class)
    public ResponseEntity<Object>blockAdministratorException(HttpServletRequest request,
                                                       BlockAdministratorException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
    @ExceptionHandler (value = BreachedPasswordException .class)
    public ResponseEntity<Object>breachedPasswordException(HttpServletRequest request,
                                                      BreachedPasswordException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
    @ExceptionHandler (value = CombinedRolesException .class)
    public ResponseEntity<Object>combinedRolesException(HttpServletRequest request,
                                                       CombinedRolesException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
    @ExceptionHandler (value = RemoveAdministratorException .class)
    public ResponseEntity<Object>removeAdministratorException(HttpServletRequest request,
                                                       RemoveAdministratorException e) {
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
                                                       SamePasswordException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
    @ExceptionHandler (value = UserLockedException .class)
    public ResponseEntity<Object>userLockedException(HttpServletRequest request,
                                                     UserLockedException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
    @ExceptionHandler (value = UserHasNotRoleSuchException .class)
    public ResponseEntity<Object>userHasNotRoleSuchException(HttpServletRequest request,
                                                             UserHasNotRoleSuchException  e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest,
                e.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
}
