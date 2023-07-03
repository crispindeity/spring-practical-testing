package study.cafekiosk.spring.domain.product;

import org.springframework.stereotype.Component;

@Component
public class ProductNumberFactory {

    private final ProductRepository productRepository;

    public ProductNumberFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String generateNextProductNumber() {
        String firstProductNumber = "001";
        String latestProductNumber = productRepository.findLatestProduct().orElse(firstProductNumber);
        if (latestProductNumber.equals(firstProductNumber)) {
            return latestProductNumber;
        }
        int nextProductNumber = Integer.parseInt(latestProductNumber) + 1;
        return String.format("%03d", nextProductNumber);
    }
}
