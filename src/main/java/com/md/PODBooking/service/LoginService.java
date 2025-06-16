package com.md.PODBooking.service;

import com.md.PODBooking.dto.AuthenticationResponse;

public interface LoginService {
    AuthenticationResponse login(String phone, String password);
    String refreshAccessToken(String refreshToken);
}
