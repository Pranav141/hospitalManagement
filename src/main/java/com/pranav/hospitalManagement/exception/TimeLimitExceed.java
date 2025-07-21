package com.pranav.hospitalManagement.exception;

public class TimeLimitExceed extends RuntimeException {
    public TimeLimitExceed(String message) {
        super(message);
    }
}
