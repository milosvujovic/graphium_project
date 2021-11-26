package uk.ac.cardiff.team5.graphium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uk.ac.cardiff.team5.graphium.service.EmailSenderService;

import java.security.SecureRandom;

@SpringBootApplication
public class GraphiumApplication {

    @Autowired
    private EmailSenderService senderService;

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    public static void main(String[] args) {
        SpringApplication.run(GraphiumApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        senderService.sendEmail("mohammed.s.hussain@outlook.com", "NEW USER", "NEW USER");
    }

}
