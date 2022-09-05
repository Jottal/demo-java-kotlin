package demo.demo.exception;

import org.springframework.http.HttpStatus;

public class SpecificErrorException extends ExceptionRun {

    public SpecificErrorException(String message, HttpStatus status) {
        super(message, status);
    }
}
