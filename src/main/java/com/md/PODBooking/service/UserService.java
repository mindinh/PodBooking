package com.md.PODBooking.service;


import com.md.PODBooking.entity.User;
import com.md.PODBooking.request.UserCreateRequest;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User createUser(UserCreateRequest userCreateRequest);
    void updateUserRole(String id, String role);
    void deleteUser(String id);

}
