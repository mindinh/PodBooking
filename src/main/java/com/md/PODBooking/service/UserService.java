package com.md.PODBooking.service;


import com.md.PODBooking.entity.UserEntity;
import com.md.PODBooking.request.UserCreateRequest;

import java.util.List;

public interface UserService {
    List<UserEntity> findAllUsers();
    UserEntity createUser(UserCreateRequest userCreateRequest);
    void updateUserRole(String id, String role);
    void deleteUser(String id);

}
