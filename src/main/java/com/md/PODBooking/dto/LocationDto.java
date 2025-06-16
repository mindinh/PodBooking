package com.md.PODBooking.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.md.PODBooking.entity.Feature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor @NoArgsConstructor
public class LocationDto {
    private String name;
    private String introduction;
    private String shortDescription;
    private String address;
    private String openHours;
    private String phone;
    private List<String> availableSpaces;
    private List<String> images;
    private Map<String, List<String>> spaceImages;
    private List<Feature> features;

}
