package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.info("{404}", e.getMessage(), e);
        return new ErrorResponse(String.format("Отсутствует"));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailNotUniqueException(final MailNotUniqueException e) {
        log.info("{409}", e.getMessage(), e);
        return new ErrorResponse(String.format("Почта не уникальна"));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleRequestAlreadyConfirmedException(final RequestAlreadyConfirmedException exception) {
        log.info("{409}", exception.getMessage(), exception);
        return new ErrorResponse(String.format("Запрос уже потвержден"));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleParticipantLimitException(final ParticipantLimitException exception) {
        log.info("{409}", exception.getMessage(), exception);
        return new ErrorResponse(String.format("Превышен лимит участников"));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBadRequestException(final BadRequestException exception) {
        log.info("{400}", exception.getMessage(), exception);
        return new ErrorResponse(String.format("Запрос составлен не корректно"));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(final Exception e) {
        log.info("{400}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }
}
