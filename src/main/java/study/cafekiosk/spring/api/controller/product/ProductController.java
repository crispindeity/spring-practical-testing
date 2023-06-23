package study.cafekiosk.spring.api.controller.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.cafekiosk.spring.api.controller.product.dto.ProductGenerateRequest;
import study.cafekiosk.spring.api.BaseResponse;
import study.cafekiosk.spring.api.service.product.ProductService;
import study.cafekiosk.spring.api.service.product.response.ProductResponse;
import study.cafekiosk.spring.api.service.product.response.ProductsResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/v1/products/new")
    public BaseResponse<ProductResponse> generateProduct(@Valid @RequestBody ProductGenerateRequest request) {
        return BaseResponse.success(HttpStatus.OK, productService.generateProduct(request.toServiceRequest()));
    }

    @GetMapping("/v1/products/selling")
    public BaseResponse<ProductsResponse> getSellingProduct() {
        return BaseResponse.success(HttpStatus.OK, productService.getSellingProducts());
    }
}
