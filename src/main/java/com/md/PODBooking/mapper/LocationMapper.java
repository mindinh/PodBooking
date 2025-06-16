package com.md.PODBooking.mapper;

import com.md.PODBooking.dto.LocationDto;
import com.md.PODBooking.entity.Location;

public class LocationMapper {
    private LocationMapper() {}

    public static LocationDto mapToDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setName(location.getLocationName());
        locationDto.setAddress(location.getAddress());
        locationDto.setIntroduction(location.getLocationIntroduction());
        locationDto.setShortDescription(location.getLocationShortDescription());
        locationDto.setOpenHours(location.getOpeningHours());
        locationDto.setPhone(location.getLocationPhone());
        locationDto.setImages(location.getLocationImages());
        locationDto.setAvailableSpaces(location.getAvailableSpaces().keySet().stream().toList());
        locationDto.setFeatures(location.getLocationFeatures());
        locationDto.setSpaceImages(location.getLocationSpaceImages());
        return locationDto;
    }


}
