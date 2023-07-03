package study.cafekiosk.spring.api.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.cafekiosk.spring.api.controller.product.dto.ProductGenerateServiceRequest;
import study.cafekiosk.spring.api.service.product.response.ProductResponse;
import study.cafekiosk.spring.api.service.product.response.ProductsResponse;
import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.domain.product.ProductNumberFactory;
import study.cafekiosk.spring.domain.product.ProductRepository;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;

@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;

    public ProductService(ProductRepository productRepository, ProductNumberFactory productNumberFactory) {
        this.productRepository = productRepository;
        this.productNumberFactory = productNumberFactory;
    }

    @Transactional
    public ProductResponse generateProduct(ProductGenerateServiceRequest request) {
        String nextProductNumber = productNumberFactory.generateNextProductNumber();
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
}

