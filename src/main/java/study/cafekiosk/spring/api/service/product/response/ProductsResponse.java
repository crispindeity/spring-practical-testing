package study.cafekiosk.spring.api.service.product.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductsResponse {

    private final List<ProductResponse> productResponses;

    private ProductsResponse(List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }

    public static ProductsResponse from(List<ProductResponse> productResponses) {
        return new ProductsResponse(productResponses);
    }

    @JsonProperty(value = "products")
    public List<ProductResponse> getProductResponses() {
        return productResponses;
    }
}
