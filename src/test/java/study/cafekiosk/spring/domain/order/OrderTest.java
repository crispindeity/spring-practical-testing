package study.cafekiosk.spring.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.util.ProductSampleData;

import java.time.LocalDateTime;
import java.util.List;

class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateSalesStatusProduct();

        //when
        Order order = Order.of(products, LocalDateTime.of(2023, 6, 12, 12, 31));

        //then
        Assertions.assertThat(order.getTotalPrice())
                .isEqualTo(8500);
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT이다.")
    @Test
    void whenGeneratingOrderTheStateIsInit() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateSalesStatusProduct();

        //when
        Order order = Order.of(products, LocalDateTime.of(2023, 6, 12, 12, 31));

        //then
        Assertions.assertThat(order.getOrderStatus())
                .isEqualTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성 시 주문 등록 시간을 기록한다.")
    @Test
    void registeredDateTime() throws Exception {
        //given
        LocalDateTime registeredDateTime = LocalDateTime.of(2023, 6, 12, 12, 31);
        List<Product> products = ProductSampleData.generateSalesStatusProduct();

        //when
        Order order = Order.of(products, registeredDateTime);

        //then
        Assertions.assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }
}