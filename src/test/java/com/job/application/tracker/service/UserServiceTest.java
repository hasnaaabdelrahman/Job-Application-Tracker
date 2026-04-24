package com.job.application.tracker.service;

import com.job.application.tracker.model.entity.User;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

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
    void  getUser_shouldCallGet_whenUserExists() {
        User user = new User(null , "joe" , "00000000000" , "joe@gmail.com" , "password@123" , LocalDate.of(2002, 02 , 01) ,  Set.of("User"), new ArrayList<>()   );
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userService.get(1);
        verify(userRepository).findById(1);
    }

    @Test
    void  getUser_shouldThrowException_whenUserNotExists() {
        User user = new User(null , "joe" , "00000000000" , "joe@gmail.com" , "password@123" , LocalDate.of(2002, 02 , 01) ,  Set.of("User"), new ArrayList<>()   );
        when(userRepository.findById(999)).thenReturn(Optional.empty());
       assertThrows(ResourceNotFoundException.class , ()->
               userService.get(999));
    }

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
