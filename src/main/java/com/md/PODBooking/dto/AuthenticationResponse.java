package com.md.PODBooking.dto;

public record AuthenticationResponse (
        String accessToken,
        String refreshToken
) {}
