package com.pwr.psi.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ApiException {
    private final String message;
    private final String key;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}
