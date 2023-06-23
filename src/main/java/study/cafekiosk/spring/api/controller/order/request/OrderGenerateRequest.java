package study.cafekiosk.spring.api.controller.order.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.cafekiosk.spring.api.service.order.request.OrderGenerateServiceRequest;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderGenerateRequest {

    @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumbers;

    private OrderGenerateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

    public static OrderGenerateRequest constructorForTesting(List<String> productNumbers) {
        return new OrderGenerateRequest(productNumbers);
    }

    public OrderGenerateServiceRequest toServiceRequest() {
        return OrderGenerateServiceRequest.from(this);
    }
}
