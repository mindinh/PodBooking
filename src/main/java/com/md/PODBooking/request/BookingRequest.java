package com.md.PODBooking.request;

public record BookingRequest(
        String phoneNumber,
        String name,
        String email,
        String location,
        String arrivalDate,
        String arrivalTime,
        String space,
        String combo,
        String extraTime,
        String note,
        String paymentMethod
) {}
