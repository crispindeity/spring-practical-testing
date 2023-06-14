package study.cafekiosk.spring.api.controller.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.cafekiosk.spring.api.service.BaseResponse;
import study.cafekiosk.spring.api.service.product.ProductService;
import study.cafekiosk.spring.api.service.product.response.ProductsResponse;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("v1/products/selling")
    public BaseResponse<ProductsResponse> getSellingProduct() {
        return BaseResponse.success(productService.getSellingProducts());
    }
}
