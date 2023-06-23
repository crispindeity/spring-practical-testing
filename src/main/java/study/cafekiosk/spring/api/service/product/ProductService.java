package study.cafekiosk.spring.api.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.cafekiosk.spring.api.controller.product.dto.ProductGenerateServiceRequest;
import study.cafekiosk.spring.api.service.product.response.ProductResponse;
import study.cafekiosk.spring.api.service.product.response.ProductsResponse;
import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.domain.product.ProductRepository;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;

@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse generateProduct(ProductGenerateServiceRequest request) {
        String nextProductNumber = generateNextProductNumber();
        Product newProduct = Product.from(request, nextProductNumber);
        productRepository.save(newProduct);
        return ProductResponse.from(newProduct);
    }

    public ProductsResponse getSellingProducts() {
        return ProductsResponse.from(
                productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay())
                        .stream()
                        .map(ProductResponse::from)
                        .toList()
        );
    }

    private String generateNextProductNumber() {
        String firstProductNumber = "001";
        String latestProductNumber = productRepository.findLatestProduct().orElse(firstProductNumber);
        if (latestProductNumber.equals(firstProductNumber)) {
            return latestProductNumber;
        }
        int nextProductNumber = Integer.parseInt(latestProductNumber) + 1;
        return String.format("%03d", nextProductNumber);
    }
}

