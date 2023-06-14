package study.cafekiosk.spring.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;
    @Enumerated(EnumType.STRING)
    private ProductType type;

    private String productNumber;

    private String name;

    private int price;

    private Product(ProductSellingStatus sellingStatus, ProductType type, String productNumber, String name, int price) {
        this.sellingStatus = sellingStatus;
        this.type = type;
        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
    }

    public static Product productTestConstructor(ProductSellingStatus sellingStatus, ProductType type, String productNumber, String name, int price) {
        return new Product(sellingStatus, type, productNumber, name, price);
    }
}
