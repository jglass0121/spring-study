package toby.spring.splearn.adapter.integration;

import toby.spring.splearn.application.required.EmailSender;
import toby.spring.splearn.domain.Email;

public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("Dummy EmailSender send email : " + email);
    }
}
