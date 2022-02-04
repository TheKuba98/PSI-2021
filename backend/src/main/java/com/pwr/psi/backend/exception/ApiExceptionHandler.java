package com.pwr.psi.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    public static final String ALREADY_ASSIGNED_KEY = "alreadyAssigned";
    public static final String THESIS_FULL_KEY = "thesisFull";
    public static final String USER_NOT_FOUND_KEY = "userNotFound";
    public static final String THESIS_NOT_FOUND_KEY = "thesisNotFound";
    public static final String THESIS_NOT_AVAILABLE_KEY = "thesisNotAvailable";

    @ExceptionHandler(value = StudentAlreadyAssignedException.class)
    public ResponseEntity<Object> handleStudentAlreadyAssignedException(StudentAlreadyAssignedException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                ALREADY_ASSIGNED_KEY,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ThesisFullException.class)
    public ResponseEntity<Object> handleThesisFullException(ThesisFullException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                THESIS_FULL_KEY,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                USER_NOT_FOUND_KEY,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ThesisNotFoundException.class)
    public ResponseEntity<Object> handleThesisNotFoundException(ThesisNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                THESIS_NOT_FOUND_KEY,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ThesisNotAvailableException.class)
    public ResponseEntity<Object> handleThesisNotAvailableException(ThesisNotAvailableException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                THESIS_NOT_AVAILABLE_KEY,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AuthorsLimitReachedException.class)
    public ResponseEntity<Object> handleAuthorsLimitReachedException(AuthorsLimitReachedException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                THESIS_NOT_AVAILABLE_KEY,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}