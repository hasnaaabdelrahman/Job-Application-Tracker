package com.job.application.tracker.service.implementation;

import com.job.application.tracker.model.dto.application.ApplicationRequest;
import com.job.application.tracker.model.dto.application.ApplicationStatsRequest;
import com.job.application.tracker.model.dto.user.UserInfo;
import com.job.application.tracker.model.dto.user.UserRequest;
import com.job.application.tracker.model.dto.user.UserResponse;
import com.job.application.tracker.model.dto.user.UserUpdateRequest;
import com.job.application.tracker.model.entity.User;
import com.job.application.tracker.exceptions.DuplicateApplicationException;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.mapper.UserMapper;
import com.job.application.tracker.repository.ApplicationRepository;
import com.job.application.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements com.job.application.tracker.service.UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationRepository applicationRepository;

    @Override
    public UserResponse add(UserRequest user) {

        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateApplicationException("Email already exists");
        }
        if(userRepository.existsByPhone(user.getPhone())){
            throw new DuplicateApplicationException("Phone already exists");

        }
        User userAdded = UserMapper.toEntity(user);
        userAdded.setRoles(Set.of("ROLE_USER"));
        userAdded.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userAdded);
        return UserMapper.toDto(userAdded);
    }

    @Override
    public List<UserResponse> showAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getPhone(),
                        user.getEmail(),
                        user.getBirthDate(),
                        user.getApplications().stream()
                                .map(application -> new ApplicationRequest(application.getId(), application.getApplicationStatus()))
                                .collect(Collectors.toList()),
                        user.getRoles()
                )).toList();

    }

    @Override
    public UserResponse get(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserMapper.toDto(user);
    }

    @Override
    public void delete(Integer id) {
        if(!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id:" + id);
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserResponse update(Integer id , UserUpdateRequest userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if(userRepository.existsByEmailAndIdNot(userDto.getEmail() , id)){
            throw new DuplicateApplicationException("Email already exists");
        }
        if(userRepository.existsByPhoneAndIdNot(userDto.getPhone() ,id)){
            throw new DuplicateApplicationException("Phone already exists");

        }
         UserMapper.UpdateEntity(user , userDto);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    public ApplicationStatsRequest stats(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("user not found with id: " + userId)
        );
        return ApplicationStatsRequest
                .builder()
                .applied(applicationRepository.countAppliedApplications(user.getId()).getCount())
                .accepted(applicationRepository.countAcceptedApplications(user.getId()).getCount())
                .applications(applicationRepository.countAll(user.getId()).getCount())
                .interview(applicationRepository.countInterviewApplications(user.getId()).getCount())
                .rejected(applicationRepository.countRejectedApplications(user.getId()).getCount())
                .build();
    }

    public UserInfo profile(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user not found with id: " + userId)
        );
        return UserMapper.toUserInfo(user);
    }

}
