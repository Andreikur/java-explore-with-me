package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException exception) {
        log.info("{404}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), "Не найдено",
                HttpStatus.NOT_FOUND.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailNotUniqueException(final MailNotUniqueException exception) {
        log.info("{409}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleRequestAlreadyConfirmedException(final RequestAlreadyConfirmedException exception) {
        log.info("{409}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleParticipantLimitException(final ParticipantLimitException exception) {
        log.info("{409}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBadRequestException(final BadRequestException exception) {
        log.info("{409}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleRequestAlreadyExistsException(final RequestAlreadyExistsException exception) {
        log.info("{409}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEventIsNotPublishedException(final EventIsNotPublishedException exception) {
        log.info("{409}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleAlreadyPublishedException(final EventIsPublishedException exception) {
        log.info("{409}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleWrongTimeOfEventException(final WrongTimeException exception) {
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleCategoryUsedException(final CategoryUsedException exception) {
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistException(final DataIntegrityViolationException exception) {
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserNameAlreadyExistException(final CommentConflictException exception) {
        return new ErrorResponse(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(final Exception exception) {
        log.info("{400}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), "Integrity constraint has been violated.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(FORMATTER));
    }
}
