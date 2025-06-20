package com.md.PODBooking.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.PODBooking.dto.LocationDto;
import com.md.PODBooking.entity.Feature;
import com.md.PODBooking.entity.Location;
import com.md.PODBooking.entity.Space;
import com.md.PODBooking.exception.InsertException;
import com.md.PODBooking.exception.ResourceAlreadyExistsException;
import com.md.PODBooking.exception.ResourceNotFoundException;
import com.md.PODBooking.mapper.LocationMapper;
import com.md.PODBooking.repository.LocationsRepository;
import com.md.PODBooking.repository.SpacesRepository;
import com.md.PODBooking.request.LocationInsertRequest;
import com.md.PODBooking.service.LocationService;
import com.md.PODBooking.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationsRepository locationsRepository;
    private SpacesRepository spacesRepository;
    private ObjectMapper objectMapper;
    private S3Service s3Service;

    public LocationServiceImpl(LocationsRepository locationsRepository, SpacesRepository spacesRepository, ObjectMapper objectMapper, S3Service s3Service) {
        this.locationsRepository = locationsRepository;
        this.spacesRepository = spacesRepository;
        this.objectMapper = objectMapper;
        this.s3Service = s3Service;
    }

    @Override
    public List<LocationDto> findAllLocations() {

        return locationsRepository.findAll().stream().map(
                (item) -> {
                    LocationDto locationDto = new LocationDto();
                    locationDto.setImages(item.getLocationImages());
                    locationDto.setName(item.getLocationName());
                    locationDto.setShortDescription(item.getLocationShortDescription());
                    return locationDto;
                }
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
            Map<String, List<String>> spacesImgs = new HashMap<>();
            Map<String, Integer> spaces = objectMapper.readValue(request.availableSpaces(), new TypeReference<>() {});
            List<Feature> features = objectMapper.readValue(request.features(), new TypeReference<>() {});

            location.setLocationName(request.name());
            location.setLocationIntroduction(request.introduction());
            location.setAddress(request.address());
            location.setLocationPhone(request.phone());
            location.setOpeningHours(request.openHours());
            location.setAvailableSpaces(spaces);
            location.setLocationFeatures(features);
            for (String s : spaces.keySet()) {
                spacesImgs.put(s, new ArrayList<>());
            }
            location.setLocationSpaceImages(spacesImgs);

            locationsRepository.save(location);
        } catch (Exception e) {
            throw new InsertException("Location insert failed");
        }
    }

    @Override
    public void uploadSpaceImages(String locationName, String spaceName, MultipartFile file) {
        Location location = locationsRepository.findLocationByLocationName(locationName).orElseThrow(
                () -> new ResourceNotFoundException("Location", "Name", locationName)
        );
        Space space = spacesRepository.findBySpaceName(spaceName).orElseThrow(
                () -> new ResourceNotFoundException("Space", "Name", spaceName)
        );

        try {
            String key = s3Service.formatKey(locationName, spaceName, file.getOriginalFilename());

            if (s3Service.doesObjectExist(key)) {
                throw new ResourceAlreadyExistsException("Location Space Image", "Space Image", file.getOriginalFilename());
            }
            else {
                String imgUrl = s3Service.uploadFile(locationName, spaceName, file);
                System.out.println(imgUrl);
                location.getLocationSpaceImages().get(spaceName).add(imgUrl);

                locationsRepository.save(location);
            }

        } catch (ResourceAlreadyExistsException e) {
            throw new ResourceAlreadyExistsException("Space Image", "Space Image", file.getOriginalFilename());
        } catch (Exception e) {
            throw new InsertException("Image insert failed");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSpaceImage(String locationName, String spaceName, String fileName) {
        Location location = locationsRepository.findLocationByLocationName(locationName).orElseThrow(
                () -> new ResourceNotFoundException("Location", "Name", locationName)
        );
        String key = s3Service.formatKey(locationName, spaceName, fileName);
        System.out.println(key);
        location.getLocationSpaceImages().get(spaceName).remove("https://podbooking-images.s3.ap-southeast-1.amazonaws.com/" + key);
        locationsRepository.save(location);

        return s3Service.deleteFile(key);


    }
}
