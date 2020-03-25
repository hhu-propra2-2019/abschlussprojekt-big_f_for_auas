package mops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SuppressWarnings("PMD")
@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
public class MopsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MopsApplication.class, args);
    }

}
