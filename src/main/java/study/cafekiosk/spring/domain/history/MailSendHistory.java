package study.cafekiosk.spring.domain.history;

import lombok.Getter;
import lombok.NoArgsConstructor;
import study.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class MailSendHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromEmail;
    private String toEmail;
    private String subject;
    private String content;

    private MailSendHistory(String fromEmail, String toEmail, String subject, String content) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.subject = subject;
        this.content = content;
    }

    public static MailSendHistory of(String fromEmail, String toEmail, String subject, String content) {
        return new MailSendHistory(fromEmail, toEmail, subject, content);
    }
}
