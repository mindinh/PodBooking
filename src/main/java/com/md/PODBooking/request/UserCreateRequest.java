package com.md.PODBooking.request;

public record UserCreateRequest (
        String fullName,
        String email,
        String phoneNumber,
        String password
) {}
