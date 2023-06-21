package study.cafekiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(BindException.class)
    public BaseResponse<Object> bindException(BindException exception) {
        String message = exception.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        ErrorData errorData = generageErrorData(exception);
        return BaseResponse.of(HttpStatus.BAD_REQUEST, message, errorData);
    }

    private ErrorData generageErrorData(BindException exception) {
        String field = exception.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getField();
        Object fieldValue = exception.getBindingResult()
                .getFieldValue(field);
        return new ErrorData(field, fieldValue);
    }

    private record ErrorData(String field, Object fieldValue) {
    }
}
