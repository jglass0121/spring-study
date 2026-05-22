package toby.spring.splearn.application.provided;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import toby.spring.splearn.application.MemberService;
import toby.spring.splearn.application.required.EmailSender;
import toby.spring.splearn.application.required.MemberRepository;
import toby.spring.splearn.domain.Email;
import toby.spring.splearn.domain.Member;
import toby.spring.splearn.domain.MemberFixture;
import toby.spring.splearn.domain.MemberStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MemberRegisterManualTest {

    @Test
    void registerTestStub() {
        MemberRegister register = new MemberService(new MemberRepositoryStub(),new EmailSenderStub(),MemberFixture.createPasswordEncoder());

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

    }

    @Test
    void registerTestMock() {
        EmailSenderMock emailSenderMock = new EmailSenderMock();
        MemberRegister register = new MemberService(new MemberRepositoryStub(),emailSenderMock,MemberFixture.createPasswordEncoder());

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        assertThat(emailSenderMock.getTos()).hasSize(1);
        assertThat(emailSenderMock.getTos().getFirst())
                .isEqualTo(member.getId());

    }

    @Test
    void registerTestMockito() {
        EmailSenderMock emailSenderMock = Mockito.mock(EmailSenderMock.class);

        MemberRegister register = new MemberService(new MemberRepositoryStub(),emailSenderMock,MemberFixture.createPasswordEncoder());

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());

    }

    static class MemberRepositoryStub implements MemberRepository {
        @Override
        public Member save(Member member){
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }
    }

    static class EmailSenderStub implements EmailSender {

        @Override
        public void send(Email email, String subject, String body) {

        }
    }

    @Getter
    static class EmailSenderMock implements EmailSender {
         List<Email> tos = new ArrayList<>();
        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }
    }



}

