package com.md.PODBooking.request;

public record LocationInsertRequest (
    String name,
    String introduction,
    String address,
    String openHours,
    String phone,
    String availableSpaces,
    String features
) {}
