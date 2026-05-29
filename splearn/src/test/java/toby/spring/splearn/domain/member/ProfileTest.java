package toby.spring.splearn.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class ProfileTest {

    @Test
    void profile() {
        new Profile("tobyess");
        new Profile("tobye12");
        new Profile("toby123");
    }


    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile(""))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Profile("2113sfdfsfdsfdsfdsfdsfds"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Profile("A"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필"))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void url() {
        var profile = new Profile("tobille");

        assertThat(profile.url()).isEqualTo("@tobille");

    }
}