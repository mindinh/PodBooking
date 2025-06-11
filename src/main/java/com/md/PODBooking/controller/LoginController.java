package com.md.PODBooking.controller;


import com.md.PODBooking.dto.ResponseDto;
import com.md.PODBooking.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestParam String phone, @RequestParam String password) {
        String token = loginService.login(phone, password);

        return ResponseEntity.ok().body(new ResponseDto("200", token));
    }
}
