package toby.spring.splearn;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class SplearnApplicationTest {

    @Test
    void run() {
        MockedStatic<SpringApplication> mock = Mockito.mockStatic(SpringApplication.class);

        SplearnApplication.main(new String[0]);

        mock.verify(() -> SpringApplication.run(SplearnApplication.class, new String[0]));


    }

}