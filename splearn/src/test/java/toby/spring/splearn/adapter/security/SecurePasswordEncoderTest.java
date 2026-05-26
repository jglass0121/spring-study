package toby.spring.splearn.adapter.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurePasswordEncoderTest {

    @Test
    void securedPasswordEncoder() {
        SecurePasswordEncoder securePasswordEncoder = new SecurePasswordEncoder();

        String passwordHash = securePasswordEncoder.encode("secret");
        Assertions.assertThat(securePasswordEncoder.matches("secret",passwordHash)).isTrue();
        Assertions.assertThat(securePasswordEncoder.matches("secerts",passwordHash)).isFalse();

    }

}