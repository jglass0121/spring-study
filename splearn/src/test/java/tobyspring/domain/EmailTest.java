package tobyspring.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void equality(){
        var email1 = new Email("toby@splern.app");
        var email2 = new Email("toby@splern.app");

        assertThat(email1).isEqualTo(email2);
    }
}