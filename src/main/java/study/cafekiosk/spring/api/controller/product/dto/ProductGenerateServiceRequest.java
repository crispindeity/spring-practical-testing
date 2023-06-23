package study.cafekiosk.spring.api.controller.product.dto;

import lombok.Getter;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductGenerateServiceRequest {

    private final ProductType productType;
    private final ProductSellingStatus sellingStatus;
    private final String name;
    private final int price;

    private ProductGenerateServiceRequest(ProductGenerateRequest request) {
        this.productType = request.getProductType();
        this.sellingStatus = request.getSellingStatus();
        this.name = request.getName();
        this.price = request.getPrice();
    }

    public static ProductGenerateServiceRequest from(ProductGenerateRequest request) {
        return new ProductGenerateServiceRequest(request);
    }
}
