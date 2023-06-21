package study.cafekiosk.spring.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseResponse<T> {

    private final int code;
    private final HttpStatus status;
    private final String message;
    private final T data;

    private BaseResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> of(HttpStatus status, String message, T data) {
        return new BaseResponse<>(status,  message, data);
    }

    public static <T> BaseResponse<T> error(HttpStatus status, T data) {
        return new BaseResponse<>(status,  "error", data);
    }

    public static <T> BaseResponse<T> success(HttpStatus status, T data) {
        return new BaseResponse<>(status, "SUCCESS", data);
    }

    public static BaseResponse<Void> success(HttpStatus status) {
        return new BaseResponse<>(status, "SUCCESS", null);
    }
}
