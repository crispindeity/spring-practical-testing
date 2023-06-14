package study.cafekiosk.spring.api.service.product;

import org.springframework.stereotype.Service;
import study.cafekiosk.spring.api.service.product.response.ProductResponse;
import study.cafekiosk.spring.api.service.product.response.ProductsResponse;
import study.cafekiosk.spring.domain.product.ProductRepository;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductsResponse getSellingProducts() {
        return ProductsResponse.from(
                productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay())
                        .stream()
                        .map(ProductResponse::from)
                        .toList()
        );
    }
}
