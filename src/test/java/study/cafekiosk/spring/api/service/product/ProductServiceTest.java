package study.cafekiosk.spring.api.service.product;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.cafekiosk.spring.IntegrationTestSupport;
import study.cafekiosk.spring.api.controller.product.dto.ProductGenerateRequest;
import study.cafekiosk.spring.api.service.product.response.ProductResponse;
import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.domain.product.ProductRepository;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.cafekiosk.spring.domain.product.ProductType;
import study.cafekiosk.spring.util.ProductSampleData;

import java.util.List;

class ProductServiceTest extends IntegrationTestSupport {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록하는 경우 등록된 상품의 상품번호는 마지막에 저장된 상품의 상품번호에 1증가한 값이어야 한다.")
    @Test
    void generateProduct() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateProduct();
        productRepository.saveAll(products);
        ProductGenerateRequest request = ProductGenerateRequest.of(ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);
        String expected = String.format("%03d", products.size() + 1);

        //when
        ProductResponse response = productService.generateProduct(request.toServiceRequest());

        //then
        Assertions.assertThat(response.getProductNumber())
                .isEqualTo(expected);
    }

    @DisplayName("신규 상품을 등록 후 조회하는 경우 기존 상품과 함께 조회되어야 한다.")
    @Test
    void getProducts() throws Exception {
        //given
        List<Product> products = ProductSampleData.generateProduct();
        productRepository.saveAll(products);
        ProductGenerateRequest request = ProductGenerateRequest.of(ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);
        productService.generateProduct(request.toServiceRequest());

        //when
        List<Product> foundProduct = productRepository.findAll();

        //then
        Assertions.assertThat(foundProduct)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000),
                        Tuple.tuple("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4500),
                        Tuple.tuple("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000),
                        Tuple.tuple("004", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)
                );
    }

    @DisplayName("상품이 하나도 없을 때 신규 상품을 등록하면 상품번호는 001이어야 한다.")
    @Test
    void generateProductWhenProductIsEmpty() throws Exception {
        //given
        String firstProductNumber = "001";
        ProductGenerateRequest request = ProductGenerateRequest.of(ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);

        //when
        ProductResponse response = productService.generateProduct(request.toServiceRequest());

        //then
        Assertions.assertThat(response.getProductNumber()).isEqualTo(firstProductNumber);
    }
}