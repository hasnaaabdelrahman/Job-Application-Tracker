package com.job.application.tracker.service;

import com.job.application.tracker.model.dto.user.UserCreateDto;
import com.job.application.tracker.model.dto.user.UserGetDto;
import com.job.application.tracker.model.dto.user.UserUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserGetDto add(UserCreateDto user);
    List<UserGetDto> showAll(Pageable pageable);
    UserGetDto get(Integer id);
    void delete(Integer id);
    UserGetDto update(Integer id, UserUpdateDto user);
}
