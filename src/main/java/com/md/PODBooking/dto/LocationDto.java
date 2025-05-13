package com.md.PODBooking.dto;


import com.md.PODBooking.entity.Feature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor @NoArgsConstructor
public class LocationDto {
    private String name;
    private String introduction;
    private String address;
    private String openHours;
    private String phone;
    private List<String> availableSpaces;
    private List<String> images;
    private Map<String, List<String>> spaceImages;
    private List<Feature> features;

}
