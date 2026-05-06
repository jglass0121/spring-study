package tobyspring.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    @Test
    void createMember() {
        var member = new Member("jac@splear.app", "Toby", "secret");
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    @DisplayName("")
    void constructorNullCheck() {
        // given
        assertThatThrownBy(() -> new Member(null, "tobe", "sercret"))
                .isInstanceOf(NullPointerException.class);


        // then
    }

    @Test
    @DisplayName("")
    void activate() {
        // given
        var member = new Member("jac@splear.app", "Toby", "secret");

        // when
        member.activate();

        // then
        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    @DisplayName("")
    void activateFail() {
        // given
        var member = new Member("jac@splear.app", "Toby", "secret");

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
        var member = new Member("jac@splear.app", "Toby", "secret");
        member.activate();

        // when
        member.deactivate();

        // then
        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);

    }

    @Test
    @DisplayName("")
    void deactivateFail() {
        // given
        var member = new Member("jac@splear.app", "Toby", "secret");


        // then
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);


    }
    

}