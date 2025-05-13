package com.md.PODBooking.controller;


import com.md.PODBooking.entity.UserEntity;
import com.md.PODBooking.request.UserCreateRequest;
import com.md.PODBooking.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> userEntityList = userService.findAllUsers();

        return ResponseEntity.ok().body(userEntityList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserCreateRequest userCreateRequest) {
        userService.createUser(userCreateRequest);

        return ResponseEntity.ok().body("User added successfully");
    }

    @PutMapping("/update-role")
    public ResponseEntity<?> updateUserRole(@RequestParam String id, @RequestParam String role) {
        userService.updateUserRole(id, role);

        return ResponseEntity.ok().body("User role updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {

        return ResponseEntity.ok().body("User deleted successfully");
    }
}
