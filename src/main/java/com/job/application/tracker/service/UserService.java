package com.job.application.tracker.service;

import com.job.application.tracker.model.dto.ApplicationDto;
import com.job.application.tracker.model.dto.UserCreateDto;
import com.job.application.tracker.model.dto.UserGetDto;
import com.job.application.tracker.model.dto.UserUpdateDto;
import com.job.application.tracker.model.entity.User;
import com.job.application.tracker.exceptions.DuplicateApplicationException;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.mapper.UserMapper;
import com.job.application.tracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserGetDto add(UserCreateDto user) {

        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateApplicationException("Email already exists");
        }
        if(userRepository.existsByPhone(user.getPhone())){
            throw new DuplicateApplicationException("Phone already exists");

        }
        User userAdded = UserMapper.toEntity(user);
        userAdded.setRoles(Set.of("ROLE_USER"));
        userAdded.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userAdded);
        return UserMapper.toDto(userAdded);
    }

    @Override
    public List<UserGetDto> showAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(user -> new UserGetDto(
                        user.getId(),
                        user.getName(),
                        user.getPhone(),
                        user.getEmail(),
                        user.getBirthDate(),
                        user.getApplications().stream()
                                .map(application -> new ApplicationDto(application.getId(), application.getApplicationStatus()))
                                .collect(Collectors.toList()),
                        user.getRoles()
                )).toList();

    }

    @Override
    public UserGetDto get(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserMapper.toDto(user);
    }

    @Override
    public void delete(Integer id) {
        if(!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id:" + id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserGetDto update(Integer id ,UserUpdateDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if(userRepository.existsByEmailAndIdNot(userDto.getEmail() , id)){
            throw new DuplicateApplicationException("Email already exists");
        }
        if(userRepository.existsByPhoneAndIdNot(userDto.getPhone() ,id)){
            throw new DuplicateApplicationException("Phone already exists");

        }
         UserMapper.UpdateEntity(user , userDto);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

}
