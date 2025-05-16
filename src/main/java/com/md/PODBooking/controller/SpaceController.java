package com.md.PODBooking.controller;


import com.md.PODBooking.dto.ComboDto;
import com.md.PODBooking.dto.ResponseDto;
import com.md.PODBooking.request.SpaceInsertRequest;
import com.md.PODBooking.service.SpaceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {
    private SpaceService spaceService;
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @GetMapping
    public ResponseEntity<?> getAllSpaces() {
        return ResponseEntity.ok().body(spaceService.findAllSpaces());
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addSpace(@ModelAttribute SpaceInsertRequest spaceInsertRequest) {
        spaceService.insertSpace(spaceInsertRequest);

        return ResponseEntity.ok().body(new ResponseDto("200", "Space added successfully"));
    }

    @PostMapping("/add-combo/{name}")
    public ResponseEntity<?> addCombo(@PathVariable String name, @RequestBody ComboDto comboDto) {
        boolean isSuccess = spaceService.addCombo(name, comboDto);
        if (!isSuccess) {
            return ResponseEntity.badRequest().body(new ResponseDto("409", "Combo already exists"));
        }
        return ResponseEntity.ok().body(new ResponseDto("200", "Combo added successfully"));
    }
}
