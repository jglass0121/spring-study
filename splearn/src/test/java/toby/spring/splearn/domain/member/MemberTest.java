package toby.spring.splearn.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = MemberFixture.createPasswordEncoder();
        member = Member.register(MemberFixture.createMemberRegisterRequest(), passwordEncoder);
    }


    @Test
    void createMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();

    }


    @Test
    @DisplayName("")
    void activate() {
        // given
        assertThat(member.getDetail().getActivatedAt()).isNull();

        // when
        member.activate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
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
        assertThat(member.getDetail().getDeactivateAt()).isNotNull();


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
        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("Charlie");
        member.changeNickname("charlie2");
        assertThat(member.getNickname()).isEqualTo("charlie2");
    }

    @Test
    void changePassword() {
        member.changePassword("verysercet2", passwordEncoder);

        assertThat(member.verifyPassword("verysercet2", passwordEncoder)).isTrue();
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
        assertThatThrownBy(() -> Member.register(MemberFixture.createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(MemberFixture.createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void updateInfo() {
        member.activate();

        var updateRequest = new MemberInfoUpdateRequest("Leo", "tobd100", "자기소개");
        member.updateInfo(updateRequest);

        assertThat(member.getNickname()).isEqualTo(updateRequest.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(updateRequest.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(updateRequest.introduction());


    }

}