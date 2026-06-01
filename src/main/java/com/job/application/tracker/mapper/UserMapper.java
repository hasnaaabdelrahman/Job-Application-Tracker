package com.job.application.tracker.mapper;

import com.job.application.tracker.model.dto.application.ApplicationRequest;
import com.job.application.tracker.model.dto.user.UserRequest;
import com.job.application.tracker.model.dto.user.UserResponse;
import com.job.application.tracker.model.dto.user.UserUpdateRequest;
import com.job.application.tracker.model.entity.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static User toEntity(UserRequest dto) {
        if(dto == null) return null;

        User user = new User();
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());

        return user;
    }

    public static UserResponse toDto(User user) {

        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setApplication(user.getApplications().stream()
                .map(application -> new ApplicationRequest(application.getId(), application.getApplicationStatus()))
                .collect(Collectors.toList()));
        dto.setRoles(user.getRoles());


        return dto;
    }

    public static void UpdateEntity(User user , UserUpdateRequest dto) {
        if (dto == null || user == null) return ;
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
    }

}
