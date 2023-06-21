package study.cafekiosk.spring.domain.stock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThen() throws Exception {
        //given
        Stock stock = Stock.of("001", 1);
        int quantity = 2;

        //when
        boolean result = stock.isQuantityLessThan(quantity);

        //then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    void deductQuantity() throws Exception {
        //given
        Stock stock = Stock.of("001", 1);
        int quantity = 1;

        //when
        stock.deductQuantity(quantity);

        //then
        Assertions.assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고보다 많은 수량으로 차감 시도하는 경우 예외가 발생한다.")
    @Test
    void whenMoreThanStock() throws Exception {
        //given
        Stock stock = Stock.of("001", 1);
        int quantity = 2;

        //when & then
        Assertions.assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족합니다.");
    }
}