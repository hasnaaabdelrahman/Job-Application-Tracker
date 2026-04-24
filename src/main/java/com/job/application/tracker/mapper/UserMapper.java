package com.job.application.tracker.mapper;

import com.job.application.tracker.model.dto.ApplicationDto;
import com.job.application.tracker.model.dto.UserCreateDto;
import com.job.application.tracker.model.dto.UserGetDto;
import com.job.application.tracker.model.dto.UserUpdateDto;
import com.job.application.tracker.model.entity.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static User toEntity(UserCreateDto dto) {
        if(dto == null) return null;

        User user = new User();
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());

        return user;
    }

    public static UserGetDto toDto(User user) {

        UserGetDto dto = new UserGetDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setApplication(user.getApplications().stream()
                .map(application -> new ApplicationDto(application.getId(), application.getApplicationStatus()))
                .collect(Collectors.toList()));
        dto.setRoles(user.getRoles());


        return dto;
    }

    public static void UpdateEntity(User user , UserUpdateDto dto) {
        if (dto == null || user == null) return ;
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
    }

}
