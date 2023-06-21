package study.cafekiosk.spring.api.controller.order.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreateRequest {

    private List<String> productNumbers;

    private OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

    public static OrderCreateRequest constructorForTesting(List<String> productNumbers) {
        return new OrderCreateRequest(productNumbers);
    }
}
