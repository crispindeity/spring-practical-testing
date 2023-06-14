package study.cafekiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.cafekiosk.spring.util.ProductSampleData;

import java.util.List;

//@SpringBootTest
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateProduct();
        productRepository.saveAll(products);

        //when
        List<Product> searchedProducts = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));

        //then
        Assertions.assertThat(searchedProducts)
                .hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        Assertions.tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        Assertions.tuple("002", "카페라떼", ProductSellingStatus.HOLD)
                );
    }

    @DisplayName("원하는 판매번호를 가지고 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateProduct();
        productRepository.saveAll(products);

        //when
        List<Product> searchedProducts = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        //then
        Assertions.assertThat(searchedProducts)
                .hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        Assertions.tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        Assertions.tuple("002", "카페라떼", ProductSellingStatus.HOLD)
                );
    }

}