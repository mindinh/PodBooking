package com.md.PODBooking.service;

import com.md.PODBooking.dto.BookingsInfoResponse;
import com.md.PODBooking.entity.Combo;
import com.md.PODBooking.request.BookingRequest;

import java.util.List;
import java.util.Map;

public interface BookingService {
    BookingsInfoResponse showBookingsInfo();
    void placeBooking(BookingRequest request);
}
