package toby.spring.splearn.application.required;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import toby.spring.splearn.SplearnApplication;
import toby.spring.splearn.domain.Member;
import toby.spring.splearn.domain.MemberFixture;
import org.junit.jupiter.api.Test;
import toby.spring.splearn.domain.MemberRegisterRequest;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@ContextConfiguration(classes = SplearnApplication.class)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        Member member = Member.register(MemberFixture.createMemberRegisterRequest(), MemberFixture.createPasswordEncoder());

        assertThat(member.getId()).isNull();
        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();

    }

    @Test
    void duplicatedEmailFail() {
        Member member = Member.register(MemberFixture.createMemberRegisterRequest(), MemberFixture.createPasswordEncoder());

        memberRepository.save(member);

        Member member2 = Member.register(MemberFixture.createMemberRegisterRequest(), MemberFixture.createPasswordEncoder());

        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);

    }




}