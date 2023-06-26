package study.cafekiosk.spring.api.service.order;

import org.springframework.stereotype.Service;
import study.cafekiosk.spring.api.service.mail.MailService;
import study.cafekiosk.spring.domain.order.Order;
import study.cafekiosk.spring.domain.order.OrderRepository;
import study.cafekiosk.spring.domain.order.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderStatisticsService {

    private final OrderRepository orderRepository;
    private final MailService mailService;

    public OrderStatisticsService(OrderRepository orderRepository, MailService mailService) {
        this.orderRepository = orderRepository;
        this.mailService = mailService;
    }

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
        List<Order> orders = orderRepository.findOrdersBy(
                orderDate.atStartOfDay(),
                orderDate.plusDays(1).atStartOfDay(),
                OrderStatus.PAYMENT_COMPLETED
        );

        int totalAmount = orders.stream().mapToInt(Order::getTotalPrice).sum();

        boolean result = mailService.sendMail(
                "no-reply@cafekiosk.com",
                email,
                String.format("[매출통계] %s", orderDate),
                String.format("총 매출 합계는 %d원입니다.", totalAmount)
        );

        if (!result) {
            throw new IllegalArgumentException("매출 통계 메일 전송 실패");
        }
        return true;
    }
}
