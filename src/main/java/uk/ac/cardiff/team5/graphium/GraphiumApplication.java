package uk.ac.cardiff.team5.graphium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@SpringBootApplication
public class GraphiumApplication {
    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    public static void main(String[] args) {
        SpringApplication.run(GraphiumApplication.class, args);
    }

}
