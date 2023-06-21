package study.cafekiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.cafekiosk.spring.util.ProductSampleData;

import java.util.List;
import java.util.Optional;

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

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 가지고 온다.")
    @Test
    void findLatestProduct() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateProduct();
        productRepository.saveAll(products);

        //when
        String latestProductNumber = productRepository.findLatestProduct()
                .orElse("null");

        //then
        Assertions.assertThat(latestProductNumber)
                .isEqualTo(products.get(products.size() - 1).getProductNumber());
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올때, 상품이 하나도 없는 경우에는 null을 반환한다.")
    @Test
    void findLatestProductWhenProductIsEmpty() throws Exception {
        //when
        String latestProductNumber = productRepository.findLatestProduct()
                .orElse("null");

        //then
        Assertions.assertThat(latestProductNumber)
                .isEqualTo("null");
    }
}