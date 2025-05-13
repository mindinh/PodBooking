package com.md.PODBooking.service;

import com.md.PODBooking.dto.LocationDto;
import com.md.PODBooking.request.LocationInsertRequest;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<LocationDto> findAllLocations();
    LocationDto findLocationByName(String name);
    void insertLocation(LocationInsertRequest request);
}
