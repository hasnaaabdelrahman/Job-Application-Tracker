package com.job.application.tracker.service.implementation;

import com.job.application.tracker.Utils.JwtUtils;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.model.dto.user.LoginRequestDto;
import com.job.application.tracker.model.dto.user.UserCreateDto;
import com.job.application.tracker.model.dto.user.UserGetDto;
import com.job.application.tracker.model.entity.User;
import com.job.application.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public String login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail() , request.getPassword())
        );
        return jwtUtils.generateJwtToken(authentication);
    }
    public UserGetDto register(UserCreateDto userDto) {
        return userService.add(userDto);
    }

    public void changePassword(Integer id,String oldPassword, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "user not found with id: " + id
                )
        );
        boolean isMatched = passwordEncoder.matches(oldPassword, user.getPassword());
        if(!isMatched) {
            throw new IllegalArgumentException("password not match the current password");
        }
        if(newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");

        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
