package com.job.application.tracker.service;

import com.job.application.tracker.entity.User;
import com.job.application.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    public User add(User user) {
        User userAdded = userRepository.save(user);
        return userAdded;
    }
    public List<User> showAll() {
        return userRepository.findAll();
    }
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
    public User update(User user) {
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

}
