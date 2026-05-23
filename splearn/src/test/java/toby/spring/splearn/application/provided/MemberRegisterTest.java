package toby.spring.splearn.application.provided;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import toby.spring.splearn.SplearnTestConfiguration;
import toby.spring.splearn.domain.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(SplearnTestConfiguration.class)
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) -resource에 넣음
@Transactional
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        //TestConfiguration에서 실행
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void activate() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());

        entityManager.flush();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);

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
        checkValidation(new MemberRegisterRequest("toby@splearn.app", "Toby", "longscret"));
        checkValidation(new MemberRegisterRequest("toby@splearn.app", "______________________________Toby", "longscret"));
        checkValidation(new MemberRegisterRequest("toby@splearn.app", "Toby", "longscret"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid)).
                isInstanceOf(ConstraintViolationException.class);
    }

}
