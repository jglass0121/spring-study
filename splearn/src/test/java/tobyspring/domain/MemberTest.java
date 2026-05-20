package tobyspring.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static tobyspring.domain.MemberFixture.createMemberRegisterRequest;
import static tobyspring.domain.MemberFixture.createPasswordEncoder;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp(){
        this.passwordEncoder = createPasswordEncoder();
        member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }


    @Test
    void createMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }


    @Test
    @DisplayName("")
    void activate() {
        // given

        // when
        member.activate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    @DisplayName("")
    void activateFail() {
        // given

        // when
        member.activate();

        // then
        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);

    }


    @Test
    @DisplayName("")
    void deactivate() {
        // given
        member.activate();

        // when
        member.deactivate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);

    }

    @Test
    @DisplayName("")
    void deactivateFail() {
        // given

        // then
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);


    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("Toby");
        member.changeNickname("charlie");
        assertThat(member.getNickname()).isEqualTo("charlie");
    }

    @Test
    void changePassword() {
        member.changePassword("verysercet",passwordEncoder);

        assertThat(member.verifyPassword("verysercet",passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();
        member.activate();
        assertThat(member.isActive()).isTrue();
        member.deactivate();
        assertThat(member.isActive()).isFalse();

    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() -> Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest(), passwordEncoder);
    }




}