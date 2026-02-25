package com.htmluc.SeniorCare_System.exception;

import com.htmluc.SeniorCare_System.exception.person.ConflictCpfException;
import com.htmluc.SeniorCare_System.exception.person.InvalidCpfException;
import com.htmluc.SeniorCare_System.model.ExceptionModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler
{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionModel> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request)
    {
        ExceptionModel err = new ExceptionModel(Instant.now(), HttpStatus.NOT_FOUND.value(), "Resource Not Found", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<ExceptionModel> invalidCpf(InvalidCpfException e, HttpServletRequest request)
    {
        ExceptionModel err = new ExceptionModel(Instant.now(), HttpStatus.BAD_REQUEST.value(), "Invalid CPF", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ConflictCpfException.class)
    public ResponseEntity<ExceptionModel> conflictCpf(InvalidCpfException e, HttpServletRequest request)
    {
        ExceptionModel err = new ExceptionModel(Instant.now(), HttpStatus.CONFLICT.value(), "Invalid CPF", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionModel> businessException(BusinessException e, HttpServletRequest request)
    {
        ExceptionModel err = new ExceptionModel(Instant.now(), HttpStatus.UNPROCESSABLE_CONTENT.value(), "Business rule violated", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(err);
    }
}