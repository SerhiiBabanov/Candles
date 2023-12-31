package com.candles.exception;

import com.candles.features.xlsxIntegration.XlsxServiceException;
import com.candles.features.order.OrderValidateException;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends
        ResponseEntityExceptionHandler {

    @ExceptionHandler({RepositoryConstraintViolationException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        RepositoryConstraintViolationException nevEx =
                (RepositoryConstraintViolationException) ex;

        String errors = nevEx.getErrors().getAllErrors().stream()
                .map(ObjectError::toString).collect(Collectors.joining("\n"));

        return new ResponseEntity<>(errors, new HttpHeaders(),
                HttpStatus.PARTIAL_CONTENT);
    }

    @ExceptionHandler({XlsxServiceException.class})
    public ResponseEntity<Object> handleWrongDataFormatException(
            Exception ex, WebRequest request) {
        String errors = ex.getMessage();
        return new ResponseEntity<>(errors, new HttpHeaders(),
                HttpStatus.PARTIAL_CONTENT);
    }

    @ExceptionHandler({OrderValidateException.class})
    public ResponseEntity<Object> handleOrderValidateException(
            Exception ex, WebRequest request) {
        String errors = ex.getMessage();
        return new ResponseEntity<>(errors, new HttpHeaders(),
                HttpStatus.PARTIAL_CONTENT);
    }
}
