package study.cafekiosk.spring.api.controller.product.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.cafekiosk.spring.domain.product.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductGenerateRequest {

    @NotNull(message = "상품 타입은 필수 입니다.")
    private ProductType productType;
    @NotNull(message = "상품 판매 상태는 필수 입니다.")
    private ProductSellingStatus sellingStatus;
    @NotBlank(message = "상품 이름은 필수 입니다.")
    private String name;
    @Positive(message = "상품 가격은 양수여야 합니다.")
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

    public ProductGenerateServiceRequest toServiceRequest() {
        return ProductGenerateServiceRequest.from(this);
    }
}
