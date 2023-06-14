package study.cafekiosk.spring.api.service.order.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import study.cafekiosk.spring.api.service.product.response.ProductResponse;
import study.cafekiosk.spring.api.service.product.response.ProductsResponse;
import study.cafekiosk.spring.domain.order.Order;
import study.cafekiosk.spring.domain.orderproduct.OrderProduct;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    @JsonIgnore
    private final Order order;

    private OrderResponse(Order order) {
        this.order = order;
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(order);
    }

    public Long getId() {
        return order.getId();
    }

    public int getTotalPrice() {
        return order.getTotalPrice();
    }

    public LocalDateTime getRegisteredDateTime() {
        return order.getRegisteredDateTime();
    }

    public ProductsResponse getProducts() {
        List<ProductResponse> productResponse = order.getOrderProducts()
                .stream()
                .map(OrderProduct::getProduct)
                .map(ProductResponse::from)
                .toList();
        return ProductsResponse.from(productResponse);
    }
}
