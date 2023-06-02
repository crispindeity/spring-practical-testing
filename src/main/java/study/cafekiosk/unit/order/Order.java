package study.cafekiosk.unit.order;

import lombok.Getter;
import study.cafekiosk.unit.beverage.Beverage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Order {

    private final LocalDateTime localDateTime;
    private final List<Beverage> beverages;

    public Order(LocalDateTime localDateTime, List<Beverage> beverages) {
        this.localDateTime = localDateTime;
        this.beverages = beverages;
    }
}
