package ru.yandex.practicum.exceptions;

import java.io.Serial;

public class ValidationException extends RuntimeException{
    @Serial
    private static final long serialUIDNumber =-704458634539L;
    private String message;

    public ValidationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
