package toby.spring.splearn.adapter.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import toby.spring.splearn.domain.shared.Email;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();
        dummyEmailSender.send(new Email("toby@splaen.app"), "subject", "body");

        Assertions.assertThat(out.capturedLines()[0]).isEqualTo("Dummy EmailSender send email : Email[address=toby@splaen.app]");


    }

}