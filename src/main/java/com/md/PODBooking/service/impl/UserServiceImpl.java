package com.md.PODBooking.service.impl;

import com.md.PODBooking.entity.Role;
import com.md.PODBooking.entity.Status;
import com.md.PODBooking.entity.User;
import com.md.PODBooking.exception.ResourceNotFoundException;
import com.md.PODBooking.repository.UsersRepository;
import com.md.PODBooking.request.UserCreateRequest;
import com.md.PODBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UsersRepository usersRepository;
    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> findAllUsers() {

        return usersRepository.findAll();
    }

    @Override
    public User createUser(UserCreateRequest userCreateRequest) {
        Optional<User> userEntity = usersRepository.findByUserEmail(userCreateRequest.email());

        if (userEntity.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUserEmail(userCreateRequest.email());
        user.setUserPhone(userCreateRequest.phoneNumber());
        user.setFullName(userCreateRequest.fullName());
        user.setPassword(passwordEncoder.encode(userCreateRequest.password()));
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.CUSTOMER);
        user.setCreatedAt(LocalDateTime.now());

        usersRepository.save(user);

        return user;
    }

    @Override
    public void updateUserRole(String id, String role) {
        Optional<User> userEntity = usersRepository.findById(id);

        if (userEntity.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userEntity.get();
        try {
            user.setRole(Role.valueOf(role));

        } catch (Exception e) {
            throw new ResourceNotFoundException("Role", "Role Name", role);
        }
        user.setUpdatedAt(LocalDateTime.now());
        usersRepository.save(user);


    }

    @Override
    public void deleteUser(String id) {
        Optional<User> userEntity = usersRepository.findById(id);

        if (userEntity.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userEntity.get();
        user.setStatus(Status.INACTIVE);
        user.setUpdatedAt(LocalDateTime.now());
        usersRepository.save(user);
    }
}
