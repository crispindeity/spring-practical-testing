package study.cafekiosk.spring.api.service.order.request;

import lombok.Getter;
import study.cafekiosk.spring.api.controller.order.request.OrderGenerateRequest;

import java.util.List;

@Getter
public class OrderGenerateServiceRequest {

    private final List<String> productNumbers;

    private OrderGenerateServiceRequest(OrderGenerateRequest orderGenerateRequest) {
        this.productNumbers = orderGenerateRequest.getProductNumbers();
    }

    public static OrderGenerateServiceRequest from(OrderGenerateRequest orderGenerateRequest) {
        return new OrderGenerateServiceRequest(orderGenerateRequest);
    }
}
