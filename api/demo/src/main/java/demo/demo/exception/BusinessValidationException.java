package demo.demo.exception;

import org.springframework.http.HttpStatus;

public class BusinessValidationException extends ExceptionRun {

    public BusinessValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
