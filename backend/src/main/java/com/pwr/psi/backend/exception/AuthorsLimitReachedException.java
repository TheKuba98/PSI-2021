package com.pwr.psi.backend.exception;

public class AuthorsLimitReachedException extends Exception{
    public AuthorsLimitReachedException(String message) {
        super(message);
    }
}
