package com.myorg.stock.exception;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, StockBadRequestException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        val status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = StockNotFoundException.class)
    protected ResponseEntity<Object> handlePartyNotFoundException(RuntimeException ex, WebRequest request) {
        val status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), status, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Object exposedBody = body;
        if (status.is4xxClientError()) {
            log.debug("Request client error", ex);
            exposedBody = ex.getLocalizedMessage();
        } else if (status.is5xxServerError()) {
            log.warn("Request server error", ex);
            exposedBody = "An error occurred";
        }
        return new ResponseEntity<>(exposedBody, headers, status);
    }
}
