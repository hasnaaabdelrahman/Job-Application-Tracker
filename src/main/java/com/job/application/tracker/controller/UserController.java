package com.job.application.tracker.controller;

import com.job.application.tracker.dto.UserCreateDto;
import com.job.application.tracker.dto.UserGetDto;
import com.job.application.tracker.dto.UserUpdateDto;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.service.CustomUserDetails;
import com.job.application.tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2- User")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "1- Get user")
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserGetDto> get(@PathVariable("id") Integer id) {
        final UserGetDto user = userService.get(id);
        return ResponseEntity.ok(user);
    }
    @Operation(summary = "2- Get all users")
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserGetDto>>
    getAll(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size ,
                                                   @RequestParam(defaultValue = "id") String sortBy ,
                                                   @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page , size , sort);
       final List<UserGetDto> users = userService.showAll(pageable);
        return ResponseEntity.ok(users);
    }
    @Operation(summary = "3- Update user")
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id
            ,@Valid @RequestBody UserUpdateDto userDto
         ,@AuthenticationPrincipal CustomUserDetails userDetails) {

        if(!id.equals(userDetails.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You cannot edit another user's data.");
        }
        final UserGetDto updated = userService.update(id ,userDto);
        return ResponseEntity.ok(updated);
    }
    @Operation(summary = "4- Delete user")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
