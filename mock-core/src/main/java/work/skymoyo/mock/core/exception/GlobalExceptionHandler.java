package work.skymoyo.mock.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e) {
        String message = Optional.ofNullable(e.getCause()).orElse(e).getMessage();
        logger.error(message, e);
        return message;
    }


}
