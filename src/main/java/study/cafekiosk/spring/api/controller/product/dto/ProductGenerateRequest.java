package study.cafekiosk.spring.api.controller.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.cafekiosk.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductGenerateRequest {
    private ProductType productType;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    private ProductGenerateRequest(ProductType productType, ProductSellingStatus sellingStatus, String name, int price) {
        this.productType = productType;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductGenerateRequest of(ProductType productType, ProductSellingStatus sellingStatus, String name, int price) {
        return new ProductGenerateRequest(productType, sellingStatus, name, price);
    }
}
