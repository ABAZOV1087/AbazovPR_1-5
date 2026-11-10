package com.example.pr5.exception;



public class LimitExceededException extends RuntimeException {
    public LimitExceededException(String message) {
        super(message);
    }
}

