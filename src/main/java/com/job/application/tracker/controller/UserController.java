package com.job.application.tracker.controller;

import com.job.application.tracker.dto.UserCreateDto;
import com.job.application.tracker.dto.UserGetDto;
import com.job.application.tracker.dto.UserUpdateDto;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/v1")
public class UserController {
    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<User>> getAll() {
       final List<User> users = userServices.showAll();
        return ResponseEntity.ok(users);
    }
    @PostMapping("/add")
    public ResponseEntity<UserGetDto> addUser(@RequestBody UserCreateDto userDto) {
        final UserGetDto added = userServices.add(userDto);
        return ResponseEntity.ok(added);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UserGetDto> updateUser(@PathVariable("id") Integer id, @RequestBody UserUpdateDto userDto) {
        final UserGetDto updated = userServices.update(id ,userDto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id) {
        userServices.delete(id);
        return ResponseEntity.ok().build();
    }
}
