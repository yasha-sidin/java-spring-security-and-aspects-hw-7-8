package ru.overthantutor.javaspringaoptask1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.overthantutor.javaspringaoptask1.domain.Role;
import ru.overthantutor.javaspringaoptask1.service.UserCreationService;


@Configuration
@RequiredArgsConstructor
public class DBInitializerConfig {
    private final UserCreationService userCreationService;

    @Bean
    public CommandLineRunner initializeJpaData() {
        return (args) -> {
            System.out.println("Application started...");
            userCreationService.createUser("user", "password", "Bob", "Siemens", 30, Role.USER);
            userCreationService.createUser("manager", "password", "John", "Smith", 40, Role.MANAGER);
        };
    }
}
