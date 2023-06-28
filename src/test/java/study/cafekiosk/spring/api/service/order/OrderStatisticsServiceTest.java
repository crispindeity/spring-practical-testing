package study.cafekiosk.spring.api.service.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import study.cafekiosk.spring.IntegrationTestSupport;
import study.cafekiosk.spring.api.client.mail.MailSendClient;
import study.cafekiosk.spring.domain.history.MailSendHistory;
import study.cafekiosk.spring.domain.history.MailSendHistoryRepository;
import study.cafekiosk.spring.domain.order.Order;
import study.cafekiosk.spring.domain.order.OrderRepository;
import study.cafekiosk.spring.domain.order.OrderStatus;
import study.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.domain.product.ProductRepository;
import study.cafekiosk.spring.util.ProductSampleData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class OrderStatisticsServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @MockBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStaticsMail() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateSalesStatusProduct();
        productRepository.saveAll(products);
        Order order1 = Order.statusBy(products, OrderStatus.PAYMENT_COMPLETED, LocalDateTime.of(2023, 3, 4, 23, 59));
        Order order2 = Order.statusBy(products, OrderStatus.PAYMENT_COMPLETED, LocalDateTime.of(2023, 3, 5, 11, 12));
        Order order3 = Order.statusBy(products, OrderStatus.PAYMENT_COMPLETED, LocalDateTime.of(2023, 3, 5, 23, 59));
        Order order4 = Order.statusBy(products, OrderStatus.PAYMENT_COMPLETED, LocalDateTime.of(2023, 3, 6, 0, 0));
        orderRepository.saveAll(List.of(order1, order2, order3, order4));

        //stubbing
        Mockito.when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        //when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

        //then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("결제완료 주문 내역을 조회 하면 조회 내역에 맞는 총 매출을 반환한다.")
    @Test
    void sendOrderStaticsMailHistory() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateSalesStatusProduct();
        productRepository.saveAll(products);
        Order order1 = Order.statusBy(products, OrderStatus.PAYMENT_COMPLETED, LocalDateTime.of(2023, 3, 4, 23, 59));
        Order order2 = Order.statusBy(products, OrderStatus.PAYMENT_COMPLETED, LocalDateTime.of(2023, 3, 5, 11, 12));
        Order order3 = Order.statusBy(products, OrderStatus.PAYMENT_COMPLETED, LocalDateTime.of(2023, 3, 5, 23, 59));
        Order order4 = Order.statusBy(products, OrderStatus.PAYMENT_COMPLETED, LocalDateTime.of(2023, 3, 6, 0, 0));
        orderRepository.saveAll(List.of(order1, order2, order3, order4));

        //stubbing
        Mockito.when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);
        orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

        //when
        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();

        //then
        Assertions.assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 17000원입니다.");
    }
}