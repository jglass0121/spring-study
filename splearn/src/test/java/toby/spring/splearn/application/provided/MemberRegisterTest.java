package toby.spring.splearn.application.provided;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import toby.spring.splearn.SplearnApplication;
import toby.spring.splearn.SplearnTestConfiguration;
import toby.spring.splearn.domain.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(SplearnTestConfiguration.class)
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) -resource에 넣음
@Transactional
public record MemberRegisterTest(MemberRegister memberRegister) {



    @Test
    void register() {
        //TestConfiguration에서 실행
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }


    @Test
    void duplicateEmailFail() {
        //TestConfiguration에서 실행
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest())
        ).isInstanceOf(DuplicateEmailException.class);

    }

    @Test
    void memberRegisterRequestFail() {
        extracted(new MemberRegisterRequest("toby@splearn.app", "Toby", "longscret"));
        extracted(new MemberRegisterRequest("toby@splearn.app", "______________________________Toby", "longscret"));
        extracted(new MemberRegisterRequest("toby@splearn.app", "Toby", "longscret"));
    }

    private void extracted(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid)).
                isInstanceOf(ConstraintViolationException.class);
    }

}
