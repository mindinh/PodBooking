package com.md.PODBooking.controller;


import com.md.PODBooking.dto.ComboDto;
import com.md.PODBooking.dto.ResponseDto;
import com.md.PODBooking.request.SpaceInsertRequest;
import com.md.PODBooking.request.SpaceUpdateRequest;
import com.md.PODBooking.service.S3Service;
import com.md.PODBooking.service.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "CRUD REST APIs for Spaces"
)
@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    private final SpaceService spaceService;
    private final S3Service s3Service;

    public SpaceController(SpaceService spaceService, S3Service s3Service) {
        this.spaceService = spaceService;
        this.s3Service = s3Service;
    }

    @Operation(
            summary = "Get all Spaces REST API",
            description = "REST API to get all existing spaces"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping
    public ResponseEntity<?> getAllSpaces() {
        return ResponseEntity.ok().body(spaceService.findAllSpaces());
    }

    @Operation(
            summary = "Get Space REST API",
            description = "REST API to get existing space by name"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/{name}")
    public ResponseEntity<?> getSpace(@PathVariable String name) {
        return ResponseEntity.ok().body(spaceService);
    }

    @Operation(
            summary = "Create Space REST APIs",
            description = "REST APIs to create a new Space"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status Created"
    )
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addSpace(@ModelAttribute SpaceInsertRequest spaceInsertRequest) {
        spaceService.insertSpace(spaceInsertRequest);

        return ResponseEntity.ok().body(new ResponseDto("201", "Space added successfully"));
    }

    @PostMapping("/add-image")
    public ResponseEntity<?> addSpaceImage(@RequestParam String name, @RequestParam("file") MultipartFile file) {
        try {
            String url = s3Service.uploadFile(name, file);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }


    @Operation(
            summary = "Add Space Combo REST APIs",
            description = "REST APIs to add a new combo to a Space"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status Created"
    )
    @PostMapping("/add-combo/{name}")
    public ResponseEntity<?> addCombo(@PathVariable String name, @RequestBody ComboDto comboDto) {
        boolean isSuccess = spaceService.addCombo(name, comboDto);
        if (!isSuccess) {
            return ResponseEntity.badRequest().body(new ResponseDto("409", "Combo already exists"));
        }
        return ResponseEntity.ok().body(new ResponseDto("201", "Combo added successfully"));
    }

    @PostMapping("/add-location/{spaceName}")
    public ResponseEntity<?> addSpaceLocation(@PathVariable String spaceName, @RequestBody SpaceUpdateRequest spaceUpdateRequest) {
        boolean isSuccess = spaceService.addSpaceLocations(spaceUpdateRequest);
        if (!isSuccess) {
            return  ResponseEntity.badRequest().body(new ResponseDto("409", "Space already exists at this location"));
        }
        return ResponseEntity.ok().body(new ResponseDto("201", "Space updated successfully"));

    }

    @DeleteMapping("/remove-combo/{spaceName}/{comboName}")
    public ResponseEntity<?> removeCombo(@PathVariable String spaceName, @PathVariable String comboName) {
        boolean isSuccess = spaceService.deleteCombo(spaceName, comboName);
        if (!isSuccess) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(new ResponseDto("204", "Combo removed successfully"));
    }
}
