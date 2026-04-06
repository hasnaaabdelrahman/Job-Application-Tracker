package com.job.application.tracker.service;

import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    void deleteUser_shouldThrowException_whenUserIsNotFound() {
        when(userRepository.existsById(999)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,
                ()->userService.delete(999)
        );

    }
    @Test
    void deleteUser_shouldCallDelete_whenUserIsFound() {
        when(userRepository.existsById(1)).thenReturn(true);
        userService.delete(1);
        verify(userRepository).existsById(1);
    }


}
