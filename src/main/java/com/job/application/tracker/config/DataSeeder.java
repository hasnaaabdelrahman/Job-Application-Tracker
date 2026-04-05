package com.job.application.tracker.config;

import com.job.application.tracker.entity.User;
import com.job.application.tracker.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            User admin = new User();
            admin.setName("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPhone("00000000000");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setBirthDate(LocalDate.of(2000, 1, 1));
            admin.setRoles(Set.of("ROLE_ADMIN"));
            userRepository.save(admin);
        }
    }
}
