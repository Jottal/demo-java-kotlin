package demo.demo.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import demo.demo.controller.response.ErrorResponse;
import demo.demo.exception.ExceptionRun;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerErrorInterceptor {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("An error ocurred", exception);

        ErrorResponse response = ErrorResponse.builder()
                .message("An unexpected error ocurred")
                .Status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleExceptionRun(ExceptionRun exceptionRun) {

        ErrorResponse response = ErrorResponse.builder()
                .message(exceptionRun.getMessage())
                .Status(exceptionRun.getStatus().value())
                .build();

        return new ResponseEntity<>(response, exceptionRun.getStatus());
    }
}