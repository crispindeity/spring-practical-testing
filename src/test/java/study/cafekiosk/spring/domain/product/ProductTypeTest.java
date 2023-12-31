package study.cafekiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType() throws Exception {
        //given
        ProductType givenType = ProductType.HANDMADE;

        //when
        boolean actual = ProductType.containsStockType(givenType);

        //then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2 () throws Exception {
        //given
        ProductType givenType = ProductType.BAKERY;

        //when
        boolean actual = ProductType.containsStockType(givenType);

        //then
        Assertions.assertThat(actual).isTrue();
    }
}