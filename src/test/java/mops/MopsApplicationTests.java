package mops;

import mops.config.H2DatabaseConfigForTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SuppressWarnings("PMD")
@ActiveProfiles("test")
@SpringBootTest(classes = {MopsApplication.class, H2DatabaseConfigForTests.class})
class MopsApplicationTests {

    @Test
    void contextLoads() {

    }

}
