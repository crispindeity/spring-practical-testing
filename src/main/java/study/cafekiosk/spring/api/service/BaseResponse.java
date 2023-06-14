package study.cafekiosk.spring.api.service;

import lombok.Getter;

@Getter
public class BaseResponse<T> {

    private final String resultCode;
    private final T result;

    private BaseResponse(String resultCode, T result) {
        this.resultCode = resultCode;
        this.result = result;
    }

    private BaseResponse(String resultCode) {
        this.resultCode = resultCode;
        this.result = null;
    }

    public static BaseResponse<Void> error(String errorCode) {
        return new BaseResponse<>(errorCode, null);
    }

    public static <T> BaseResponse<T> success(T result) {
        return new BaseResponse<>("SUCCESS", result);
    }

    public static BaseResponse<Void> success() {
        return new BaseResponse<>("SUCCESS", null);
    }
}
