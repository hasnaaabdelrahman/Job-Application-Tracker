package com.job.application.tracker.controller;

import com.job.application.tracker.dto.UserCreateDto;
import com.job.application.tracker.dto.UserGetDto;
import com.job.application.tracker.dto.UserUpdateDto;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserGetDto> get(@PathVariable("id") Integer id) {
        final UserGetDto user = userService.get(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserGetDto>> getAll() {
       final List<UserGetDto> users = userService.showAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserGetDto> updateUser(@PathVariable("id") Integer id,@Valid @RequestBody UserUpdateDto userDto) {
        final UserGetDto updated = userService.update(id ,userDto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
