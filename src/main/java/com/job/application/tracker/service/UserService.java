package com.job.application.tracker.service;

import com.job.application.tracker.model.dto.user.UserRequest;
import com.job.application.tracker.model.dto.user.UserResponse;
import com.job.application.tracker.model.dto.user.UserUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponse add(UserRequest user);
    List<UserResponse> showAll(Pageable pageable);
    UserResponse get(Integer id);
    void delete(Integer id);
    UserResponse update(Integer id, UserUpdateRequest user);
}
