package ru.practicum.exception;

public class MailNotUniqueException extends RuntimeException {
    public MailNotUniqueException(String message) {
        super(message);
    }
}
