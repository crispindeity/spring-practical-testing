package study.cafekiosk.spring.api.controller.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import study.cafekiosk.spring.api.BaseResponse;
import study.cafekiosk.spring.api.service.order.OrderService;
import study.cafekiosk.spring.api.service.order.response.OrderResponse;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/v1/orders/new")
    public BaseResponse<Long> createOrder(@RequestBody OrderCreateRequest request) {
        LocalDateTime registeredDateAt = LocalDateTime.now();
        OrderResponse response = orderService.createOrder(request, registeredDateAt);
        return BaseResponse.success(HttpStatus.OK, response.getId());
    }
}

