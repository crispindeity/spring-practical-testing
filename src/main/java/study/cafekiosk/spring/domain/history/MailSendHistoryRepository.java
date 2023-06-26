package study.cafekiosk.spring.domain.history;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MailSendHistoryRepository extends JpaRepository<MailSendHistory, Long> {
}
