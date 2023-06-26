package study.cafekiosk.spring.api.service.mail;

import org.springframework.stereotype.Service;
import study.cafekiosk.spring.api.client.mail.MailSendClient;
import study.cafekiosk.spring.domain.history.MailSendHistory;
import study.cafekiosk.spring.domain.history.MailSendHistoryRepository;

@Service
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public MailService(MailSendClient mailSendClient, MailSendHistoryRepository mailSendHistoryRepository) {
        this.mailSendClient = mailSendClient;
        this.mailSendHistoryRepository = mailSendHistoryRepository;
    }

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);
        if (result) {
            mailSendHistoryRepository.save(MailSendHistory.of(fromEmail, toEmail, subject, content));
            return true;
        }
        return false;
    }
}
