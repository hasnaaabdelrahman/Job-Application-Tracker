package com.job.application.tracker.repository;

import com.job.application.tracker.entity.User;

import org.h2.engine.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;
    @BeforeEach
    void setUp() {
         user = new User(null , "joe" , "00000000000" , "joe@gmail.com" , "password@123" , LocalDate.of(2002, 02 , 01) ,  Set.of("User"), new ArrayList<>()   );
        userRepository.save(user);
    }

    @Test
    public void existsById_shouldReturnTrue_whenUserIsSaved() {
        boolean exists = userRepository.existsById(user.getId());
        assertThat(exists).isTrue();
    }

    @Test
    public void existsById_shouldReturnFalse_whenUserDoesNotExists() {
        boolean exists = userRepository.existsById(999);
        assertThat(exists).isFalse();
    }

    @Test
    public void existsByEmail_shouldReturnTrue_whenEmailExists() {
        boolean exists = userRepository.existsByEmail(user.getEmail());
        assertThat(exists).isTrue();
    }
    @Test
    public void existsByEmail_shouldReturnFalse_whenEmailDoesNotExists() {
        boolean exists = userRepository.existsByEmail("notfound@gmail.com");
        assertThat(exists).isFalse();
    }

    @Test
    public void existsByPhone_shouldReturnTrue_whenPhoneExists() {
        boolean exists = userRepository.existsByPhone(user.getPhone());
        assertThat(exists).isTrue();
    }
    @Test
    public void existsByPhone_shouldReturnFalse_whenPhoneDoesNotExists() {
        boolean exists = userRepository.existsByPhone("0000000000");
        assertThat(exists).isFalse();
    }

    @Test
    public void existsByEmailAndIdNot_shouldReturnTrue_whenEmailExistsForDifferentUser() {
        boolean exists = userRepository.existsByEmailAndIdNot("joe@gmail.com" , 999);
        assertThat(exists).isTrue();
    }
    @Test
    public void existsByEmailAndIdNot_shouldReturnTrue_whenEmailBelongsToSameUser() {
        boolean exists = userRepository.existsByEmailAndIdNot("joe@gmail.com" , user.getId());
        assertThat(exists).isFalse();
    }

    @Test
    public void existsByPhoneAndIdNot_shouldReturnTrue_whenPhoneExistsForDifferentUser() {
        boolean exists = userRepository.existsByPhoneAndIdNot("00000000000" , 999);
        assertThat(exists).isTrue();
    }
    @Test
    public void existsByPhoneAndIdNot_shouldReturnTrue_whenPhoneBelongsToSameUser() {
        boolean exists = userRepository.existsByPhoneAndIdNot("00000000000" , user.getId());
        assertThat(exists).isFalse();
    }
}
