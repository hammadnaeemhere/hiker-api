package com.element.hikers.backend.exceptions;

import com.element.hikers.backend.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);

        FieldError fieldError = ex.getBindingResult().getFieldError();
        ResponseDto responseDTO = ResponseDto.builder()
                .message(fieldError.getField() + " - " + Objects.requireNonNull(fieldError).getDefaultMessage()).build();

        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ResponseDto> handleConstraintViolationException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto responseDTO;
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException ve = (ConstraintViolationException) ex;
            responseDTO = ResponseDto.builder()
                    .message(ve.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","))).build();

        } else {
            responseDTO = ResponseDto.builder()
                    .message(ex.getLocalizedMessage()).build();
        }


        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(InvalidAgeException.class)
    public final ResponseEntity<ResponseDto> handleInvalidAgeException(InvalidAgeException e) {
        ResponseDto responseDTO = ResponseDto
                .builder()
                .message("Hiker's " + e.getEmail() + " age must be between " + e.getMinAge() + " - " + e.getMaxAge()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);

    }

    @ExceptionHandler(InvalidBookingKeyException.class)
    public final ResponseEntity<ResponseDto> handleInvalidBookingKeyException() {
        ResponseDto responseDTO = ResponseDto
                .builder()
                .message("No booking exists with provided booking id and booking key").build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);

    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseDto responseDTO = ResponseDto
                .builder()
                .message(ex.getParameterName() + " query parameter is missing.").build();
        return  ResponseEntity.status(status).body(responseDTO);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(ex instanceof MethodArgumentTypeMismatchException){
            MethodArgumentTypeMismatchException mex = (MethodArgumentTypeMismatchException)ex;
            ResponseDto responseDTO = ResponseDto
                    .builder()
                    .message(mex.getName()+ " has invalid format or type.").build();
            return  ResponseEntity.status(status).body(responseDTO);
        }

        return  ResponseEntity.status(status).body(null);
    }
}
