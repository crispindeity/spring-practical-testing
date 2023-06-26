package study.cafekiosk.spring.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT o FROM Order as o WHERE o.registeredDateTime >= :startDateTime and o.registeredDateTime < :endDateTime" +
            " and o.orderStatus = :orderStatus")
    List<Order> findOrdersBy(LocalDateTime startDateTime, LocalDateTime endDateTime, OrderStatus orderStatus);
}
