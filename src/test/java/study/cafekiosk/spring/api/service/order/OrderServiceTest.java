package study.cafekiosk.spring.api.service.order;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import study.cafekiosk.spring.api.service.order.response.OrderResponse;
import study.cafekiosk.spring.domain.order.OrderRepository;
import study.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.domain.product.ProductRepository;
import study.cafekiosk.spring.domain.stock.Stock;
import study.cafekiosk.spring.domain.stock.StockRepository;
import study.cafekiosk.spring.util.ProductSampleData;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateSalesStatusProduct();
        productRepository.saveAll(products);
        OrderCreateRequest request = OrderCreateRequest.constructorForTesting(List.of("001", "002"));
        LocalDateTime registeredDateTime = LocalDateTime.of(2023, 6, 12, 12, 37);

        //when
        OrderResponse response = orderService.createOrder(request, registeredDateTime);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getId())
                    .isNotNull();
            softly.assertThat(response.getRegisteredDateTime())
                    .isEqualTo(registeredDateTime);
            softly.assertThat(response.getProducts().getProductResponses())
                    .hasSize(2)
                    .extracting("productNumber", "price")
                    .containsExactlyInAnyOrder(
                            Tuple.tuple("001", 4000),
                            Tuple.tuple("002", 4500)
                    );
        });
    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithSameProductNumbers() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateProduct();
        productRepository.saveAll(products);
        OrderCreateRequest request = OrderCreateRequest.constructorForTesting(List.of("001", "001"));
        LocalDateTime registeredDateTime = LocalDateTime.of(2023, 6, 12, 12, 37);

        //when
        OrderResponse response = orderService.createOrder(request, registeredDateTime);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getId())
                    .isNotNull();
            softly.assertThat(response.getRegisteredDateTime())
                    .isEqualTo(registeredDateTime);
            softly.assertThat(response.getTotalPrice())
                    .isEqualTo(8000);
            softly.assertThat(response.getProducts().getProductResponses())
                    .hasSize(2)
                    .extracting("productNumber", "price")
                    .containsExactlyInAnyOrder(
                            Tuple.tuple("001", 4000),
                            Tuple.tuple("001", 4000)
                    );
        });
    }

    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrderWithStock() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateStockProduct();
        productRepository.saveAll(products);
        List<Stock> stocks = List.of(
                Stock.of("001", 2),
                Stock.of("002", 2)
        );
        stockRepository.saveAll(stocks);
        OrderCreateRequest order = OrderCreateRequest.constructorForTesting(List.of("001", "001", "002", "003"));
        LocalDateTime registeredDateTime = LocalDateTime.of(2023, 6, 14, 23, 12);

        //when
        OrderResponse response = orderService.createOrder(order, registeredDateTime);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getId())
                    .isNotNull();
            softly.assertThat(response.getRegisteredDateTime())
                    .isEqualTo(registeredDateTime);
            softly.assertThat(response.getTotalPrice())
                    .isEqualTo(38000);
            softly.assertThat(response.getProducts().getProductResponses())
                    .hasSize(4)
                    .extracting("productNumber", "price")
                    .containsExactlyInAnyOrder(
                            Tuple.tuple("001", 10000),
                            Tuple.tuple("001", 10000),
                            Tuple.tuple("002", 13000),
                            Tuple.tuple("003", 5000)
                    );
            softly.assertThat(stockRepository.findAll())
                    .hasSize(2)
                    .extracting("productNumber", "quantity")
                    .containsExactlyInAnyOrder(
                            Tuple.tuple("001", 0),
                            Tuple.tuple("002", 1)
                    );
        });
    }

    @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    @Test
    void createOrderWithNoStock() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateStockProduct();
        productRepository.saveAll(products);
        List<Stock> stocks = List.of(
                Stock.of("001", 1),
                Stock.of("002", 2)
        );
        stockRepository.saveAll(stocks);
        OrderCreateRequest order = OrderCreateRequest.constructorForTesting(List.of("001", "001", "002", "003"));
        LocalDateTime registeredDateTime = LocalDateTime.of(2023, 6, 14, 23, 12);

        //when & then
        Assertions.assertThatThrownBy(() ->
                        orderService.createOrder(order, registeredDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족한 상품이 있습니다.");
    }
}
