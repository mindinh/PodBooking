package com.md.PODBooking.service;

import com.md.PODBooking.dto.LocationDto;
import com.md.PODBooking.request.LocationInsertRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<LocationDto> findAllLocations();
    LocationDto findLocationByName(String name);
    void insertLocation(LocationInsertRequest request);
    void uploadSpaceImages(String locationName, String spaceName, MultipartFile file);
}
