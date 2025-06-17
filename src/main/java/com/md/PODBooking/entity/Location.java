package com.md.PODBooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "locations")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Location {
    @Id
    private String id;
    private String locationId;
    private String locationName;
    private String locationIntroduction;
    private String locationShortDescription;
    private String address;
    private String openingHours;
    private String locationPhone;
    private Map<String, Integer> availableSpaces;
    private List<String> locationImages;
    private Map<String, List<String>> locationSpaceImages;
    private List<Feature> locationFeatures;
    private Status status;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
