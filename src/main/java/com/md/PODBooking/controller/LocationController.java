package com.md.PODBooking.controller;


import com.md.PODBooking.dto.ResponseDto;
import com.md.PODBooking.request.LocationInsertRequest;
import com.md.PODBooking.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "CRUD REST APIs for Locations"
)
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private LocationService locationService;
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(
            summary = "Get all Locations REST API",
            description = "REST API to get all existing locations"
    )
    @GetMapping
    public ResponseEntity<?> getAllLocations() {
        return ResponseEntity.ok().body(locationService.findAllLocations());

    }

    @Operation(
            summary = "Create a Location REST API",
            description = "REST API to create a new location"
    )
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addLocation(@ModelAttribute LocationInsertRequest request) {

        locationService.insertLocation(request);

        return ResponseEntity.ok().body(new ResponseDto("200", "Location inserted"));
    }

    @Operation(
            summary = "Get a Location REST API",
            description = "REST API to get existing location by name"
    )
    @GetMapping("/{name}")
    public ResponseEntity<?> getLocation(@PathVariable String name) {
        return ResponseEntity.ok().body(locationService.findLocationByName(name));

    }

    @PostMapping("/add-space-image/{locationName}/{spaceName}")
    public ResponseEntity<?> addSpaceImage(@PathVariable String locationName, @PathVariable String spaceName, MultipartFile fileImg) {
        locationService.uploadSpaceImages(locationName, spaceName, fileImg);

        return ResponseEntity.ok().body(new ResponseDto("200", "Space image uploaded"));
    }
}
