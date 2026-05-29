package toby.spring.splearn.application.member.required;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import toby.spring.splearn.domain.member.Member;
import toby.spring.splearn.domain.member.MemberFixture;
import toby.spring.splearn.domain.member.MemberStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static toby.spring.splearn.domain.member.MemberStatus.*;

@DataJpaTest
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
        entityManager.clear();

        Member found = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(found.getStatus()).isEqualTo(PENDING);
        assertThat(found.getDetail().getRegisteredAt()).isNotNull();


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