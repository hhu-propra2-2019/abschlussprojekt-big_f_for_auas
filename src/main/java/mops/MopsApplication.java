package mops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SuppressWarnings("PMD")
@SpringBootApplication
@EnableJpaRepositories
public class MopsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MopsApplication.class, args);
    }

}
