package study.cafekiosk.spring.api.service.order;

import org.springframework.stereotype.Service;
import study.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import study.cafekiosk.spring.api.service.order.response.OrderResponse;
import study.cafekiosk.spring.domain.order.Order;
import study.cafekiosk.spring.domain.order.OrderRepository;
import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.domain.product.ProductRepository;
import study.cafekiosk.spring.domain.product.ProductType;
import study.cafekiosk.spring.domain.stock.Stock;
import study.cafekiosk.spring.domain.stock.StockRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    /**
     *
     * @param request
     * @param registeredDateTime
     * @return
     */
    // TODO: 동시성 문제 해결을 위해 로직을 개선시켜 보자.
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductBy(productNumbers);
        deductStockQuantity(products);
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

    private List<Product> generateSameProduct(List<String> productNumbers, List<Product> products) {
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, product -> product));
        return productNumbers.stream()
                .map(productMap::get)
                .toList();
    }

    private void deductStockQuantity(List<Product> products) {
        List<String> stockProductNumbers = extractStockProductNumbers(products);
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        Map<String, Stock> stockMap = generateStockMapBy(stocks);
        Map<String, Long> productCountingMap = generateCountingMapBy(stockProductNumbers);
        List<String> distinctProductNumbers = generateDistinctProductNumbersBy(stockProductNumbers);

        for (String number : distinctProductNumbers) {
            Stock stock = stockMap.get(number);
            int quantity = productCountingMap.get(number).intValue();
            if (stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }
    }

    private List<String> extractStockProductNumbers(List<Product> products) {
        return products.stream()
                .filter(product -> ProductType.containsStockType(product.getType()))
                .map(Product::getProductNumber)
                .toList();
    }

    private Map<String, Stock> generateStockMapBy(List<Stock> stocks) {
        return stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, stock -> stock));
    }

    private Map<String, Long> generateCountingMapBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));
    }

    private List<String> generateDistinctProductNumbersBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
                .distinct()
                .toList();
    }
}
