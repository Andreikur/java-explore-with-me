package ru.practicum.exception;

public class EventIsPublishedException extends RuntimeException {
    public EventIsPublishedException(String message) {
        super(message);
    }
}
