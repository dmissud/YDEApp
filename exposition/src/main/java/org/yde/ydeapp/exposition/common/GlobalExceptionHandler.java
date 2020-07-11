package org.yde.ydeapp.exposition.common;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.yde.ydeapp.domain.out.BusinessException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> notFoundExceptionHandler(final BusinessException ex) {

        final ErrorDTO error = new ErrorDTO();
        error.setMessage(ex.getMessage());
        error.setCode(ex.getCode());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValueInstantiationException.class)
    public ResponseEntity<Object> handleValueInstantiationException(ValueInstantiationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.PRECONDITION_FAILED);
        body.put("error", "Command is not valid");
        String message = ex.getLocalizedMessage();
        body.put("message", message.substring(message.indexOf("problem:") + 9, message.indexOf("\n")));
        return new ResponseEntity<>(body, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedExceptionHandler(final AccessDeniedException ex) {

        final ErrorDTO error = new ErrorDTO();
        error.setMessage(ex.getMessage());

        log.error(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultHandler(final Exception ex) {

        final ErrorDTO error = new ErrorDTO();
        error.setMessage(ex.getMessage());
        error.setCode("INTERNAL_SERVER_ERROR");

        log.error(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
