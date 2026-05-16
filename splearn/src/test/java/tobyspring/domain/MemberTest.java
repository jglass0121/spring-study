package tobyspring.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp(){
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }

        };
        member = Member.register(new MemberRegisterRequest("jac@splear.app", "Toby", "secret"), passwordEncoder);
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
        assertThatThrownBy(() -> Member.register(new MemberRegisterRequest("invalid email", "toby", "secret"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(new MemberRegisterRequest("invalid@gmail.com", "toby", "secret"), passwordEncoder);
    }


}