package toby.spring.splearn;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import toby.spring.splearn.application.member.required.EmailSender;
import toby.spring.splearn.domain.member.MemberFixture;
import toby.spring.splearn.domain.member.PasswordEncoder;

/**
 *
 * 상위로 올려서 사용할 수 있도록
 */
@TestConfiguration
public  class SplearnTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println("Sending email : " + email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
