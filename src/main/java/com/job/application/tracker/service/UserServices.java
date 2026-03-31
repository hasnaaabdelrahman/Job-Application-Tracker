package com.job.application.tracker.service;

import com.job.application.tracker.dto.UserCreateDto;
import com.job.application.tracker.dto.UserGetDto;
import com.job.application.tracker.dto.UserUpdateDto;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.mapper.UserMapper;
import com.job.application.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    public UserGetDto add(UserCreateDto user) {

        User userAdded = UserMapper.toEntity(user);
        userRepository.save(userAdded);
        return UserMapper.toDto(userAdded);
    }
    public List<UserGetDto> showAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserGetDto(user.getId() , user.getName() , user.getEmail() , user.getPhone() , user.getBirthDate()))
                .toList();
    }
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
    public UserGetDto update(Integer id ,UserUpdateDto userDto) {
        User user = userRepository.findById(id).orElseThrow();
         UserMapper.UpdateEntity(user , userDto);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

}
