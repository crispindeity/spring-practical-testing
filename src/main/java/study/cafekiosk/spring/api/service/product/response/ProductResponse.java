package study.cafekiosk.spring.api.service.product.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.cafekiosk.spring.domain.product.ProductType;

public class ProductResponse {

    @JsonIgnore
    private final Product product;

    private ProductResponse(Product product) {
        this.product = product;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product);
    }

    public long getId() {
        return this.product.getId();
    }

    public ProductType getType() {
        return this.product.getType();
    }

    public ProductSellingStatus getSellingStatus() {
        return this.product.getSellingStatus();
    }

    public String getProductNumber() {
        return this.product.getProductNumber();
    }

    public String getName() {
        return this.product.getName();
    }

    public int getPrice() {
        return this.product.getPrice();
    }
}
