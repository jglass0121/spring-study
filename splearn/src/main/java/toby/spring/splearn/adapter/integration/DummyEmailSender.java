package toby.spring.splearn.adapter.integration;

import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;
import toby.spring.splearn.application.required.EmailSender;
import toby.spring.splearn.domain.Email;

@Component
@Fallback // 다른 빈을 찾다가 찾을 수없을 때 이 빈을 사용해줘
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("Dummy EmailSender send email : " + email);
    }
}
