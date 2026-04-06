package com.job.application.tracker.service;

import com.job.application.tracker.dto.UserCreateDto;
import com.job.application.tracker.dto.UserGetDto;
import com.job.application.tracker.dto.UserUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService  {
    UserGetDto add(UserCreateDto user);
    List<UserGetDto> showAll(Pageable pageable);
    UserGetDto get(Integer id);
    void delete(Integer id);
    UserGetDto update(Integer id, UserUpdateDto user);
}
