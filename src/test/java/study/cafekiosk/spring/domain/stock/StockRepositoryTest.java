package study.cafekiosk.spring.domain.stock;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @DisplayName("상품번호 리스트로 재고를 조회한다.")
    @Test
    void findAllByProductNumberIn() throws Exception {
        //given
        List<Stock> stocks = generateStock();
        stockRepository.saveAll(stocks);

        //when
        List<Stock> response = stockRepository.findAllByProductNumberIn(List.of("001", "002"));

        //then
        Assertions.assertThat(response)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 1),
                        Tuple.tuple("002", 2)
                );
    }

    private List<Stock> generateStock() {
        return List.of(
                Stock.of("001", 1),
                Stock.of("002", 2),
                Stock.of("003", 3)
        );
    }
}