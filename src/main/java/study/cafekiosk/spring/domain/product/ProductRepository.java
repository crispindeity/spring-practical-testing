package study.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingTypes);

    List<Product> findAllByProductNumberIn(List<String> productNumbers);

    @Query(value = "SELECT product_number FROM product AS p ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLatestProduct();
}
