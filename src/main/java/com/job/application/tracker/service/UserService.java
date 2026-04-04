package com.job.application.tracker.service;

import com.job.application.tracker.dto.ApplicationDto;
import com.job.application.tracker.dto.UserCreateDto;
import com.job.application.tracker.dto.UserGetDto;
import com.job.application.tracker.dto.UserUpdateDto;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.exceptions.DuplicateApplicationException;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.mapper.UserMapper;
import com.job.application.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserGetDto add(UserCreateDto user) {

        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateApplicationException("Email already exists");
        }
        if(userRepository.existsByPhone(user.getPhone())){
            throw new DuplicateApplicationException("Phone already exists");

        }
        User userAdded = UserMapper.toEntity(user);

        userRepository.save(userAdded);
        return UserMapper.toDto(userAdded);
    }

    @Override
    public List<UserGetDto> showAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserGetDto(user.getId() , user.getName() , user.getEmail() , user.getPhone() , user.getBirthDate() ,
                        user.getApplications().stream()
                                .map(
                                application -> new ApplicationDto (application.getId() , application.getApplicationStatus())).toList()))
                .toList();

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
