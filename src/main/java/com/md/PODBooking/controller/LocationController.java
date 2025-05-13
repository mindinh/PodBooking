package com.md.PODBooking.controller;


import com.md.PODBooking.dto.ResponseDto;
import com.md.PODBooking.request.LocationInsertRequest;
import com.md.PODBooking.service.LocationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private LocationService locationService;
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLocations() {
        return ResponseEntity.ok().body(locationService.findAllLocations());

    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addLocation(@ModelAttribute LocationInsertRequest request) {

        locationService.insertLocation(request);

        return ResponseEntity.ok().body(new ResponseDto("200", "Location inserted"));
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getLocation(@PathVariable String name) {
        return ResponseEntity.ok().body(locationService.findLocationByName(name));

    }
}
