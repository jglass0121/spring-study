package toby.spring.splearn.application.member.provided;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import toby.spring.splearn.SplearnTestConfiguration;
import toby.spring.splearn.domain.member.Member;
import toby.spring.splearn.domain.member.MemberFixture;


//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void find() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        Member found = memberFinder.find(member.getId());

        Assertions.assertThat(member.getId()).isEqualTo(found.getId());

    }

    @Test
    void findFail() {
        Assertions.assertThatThrownBy(()
                        -> memberFinder.find(999L))
                .isInstanceOf(IllegalArgumentException.class);

    }
}