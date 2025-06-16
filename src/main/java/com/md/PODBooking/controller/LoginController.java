package com.md.PODBooking.controller;


import com.md.PODBooking.dto.AuthenticationResponse;
import com.md.PODBooking.dto.ResponseDto;
import com.md.PODBooking.request.RefreshTokenRequest;
import com.md.PODBooking.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestParam String phone, @RequestParam String password) {
        AuthenticationResponse authResponse = loginService.login(phone, password);

        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String newAccessToken = loginService.refreshAccessToken(request.refreshToken());

        return ResponseEntity.ok().body(new ResponseDto("200", newAccessToken));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body(new ResponseDto("200", "test"));
    }
}
