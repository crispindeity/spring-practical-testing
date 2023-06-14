package study.cafekiosk.spring.api.service.order;

import org.springframework.stereotype.Service;
import study.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import study.cafekiosk.spring.api.service.order.response.OrderResponse;
import study.cafekiosk.spring.domain.order.Order;
import study.cafekiosk.spring.domain.order.OrderRepository;
import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    /**
     *
     * @param request
     * @param registeredDateTime
     * @return
     */
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductBy(productNumbers);
        Order order = Order.of(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.from(savedOrder);
    }

    private List<Product> findProductBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        if (productNumbers.size() != products.size()) {
            return generateSameProduct(productNumbers, products);
        }
        return products;
    }

    private static List<Product> generateSameProduct(List<String> productNumbers, List<Product> products) {
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, product -> product));
        return productNumbers.stream()
                .map(productMap::get)
                .toList();
    }
}
