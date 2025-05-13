package com.md.PODBooking.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.PODBooking.dto.LocationDto;
import com.md.PODBooking.entity.Feature;
import com.md.PODBooking.entity.Location;
import com.md.PODBooking.exception.InsertException;
import com.md.PODBooking.exception.ResourceNotFoundException;
import com.md.PODBooking.mapper.LocationMapper;
import com.md.PODBooking.repository.LocationsRepository;
import com.md.PODBooking.request.LocationInsertRequest;
import com.md.PODBooking.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationsRepository locationsRepository;
    private ObjectMapper objectMapper;
    public LocationServiceImpl(LocationsRepository locationsRepository, ObjectMapper objectMapper) {
        this.locationsRepository = locationsRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<LocationDto> findAllLocations() {

        return locationsRepository.findAll().stream().map(
                LocationMapper::mapToDto
        ).toList();
    }

    @Override
    public LocationDto findLocationByName(String name) {
        Location location = locationsRepository.findLocationByLocationName(name).orElseThrow(
                () -> new ResourceNotFoundException("Location", "Name", name)
        );

        return LocationMapper.mapToDto(location);
    }


    @Override
    public void insertLocation(LocationInsertRequest request) {
        Location location = new Location();
        try {
            Map<String, Integer> spaces = objectMapper.readValue(request.availableSpaces(), new TypeReference<>() {});
            List<Feature> features = objectMapper.readValue(request.features(), new TypeReference<>() {});

            location.setLocationName(request.name());
            location.setLocationIntroduction(request.introduction());
            location.setAddress(request.address());
            location.setLocationPhone(request.phone());
            location.setOpeningHours(request.openHours());
            location.setAvailableSpaces(spaces);
            location.setLocationFeatures(features);

            locationsRepository.save(location);
        } catch (Exception e) {
            throw new InsertException("Location insert failed");
        }
    }
}
