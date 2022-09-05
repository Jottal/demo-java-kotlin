package demo.demo.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ExceptionRun {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
