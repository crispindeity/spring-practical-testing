package study.cafekiosk.spring.util;

import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

public class ProductSampleData {

    public static List<Product> generateProduct() {
        return List.of(
                Product.productTestConstructor(ProductSellingStatus.SELLING, ProductType.HANDMADE, "001", "아메리카노", 4000),
                Product.productTestConstructor(ProductSellingStatus.HOLD, ProductType.HANDMADE, "002", "카페라떼", 4500),
                Product.productTestConstructor(ProductSellingStatus.STOP_SELLING, ProductType.HANDMADE, "003", "팥빙수", 7000)
        );
    }

    public static List<Product> generateSalesStatusProduct() {
        return List.of(
                Product.productTestConstructor(ProductSellingStatus.SELLING, ProductType.HANDMADE, "001", "아메리카노", 4000),
                Product.productTestConstructor(ProductSellingStatus.SELLING, ProductType.HANDMADE, "002", "카페라떼", 4500)
        );
    }

    public static List<Product> generateStockProduct() {
        return List.of(
                Product.productTestConstructor(ProductSellingStatus.SELLING, ProductType.BOTTLE, "001", "에비앙", 10000),
                Product.productTestConstructor(ProductSellingStatus.SELLING, ProductType.BAKERY, "002", "크루와상", 13000),
                Product.productTestConstructor(ProductSellingStatus.SELLING, ProductType.HANDMADE, "003", "키위주스", 5000)
        );
    }
}
