package com.md.PODBooking.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "spaces")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Space {
    @Id
    private String id;
    private String spaceName;
    private List<String> amenities;
    private List<String> spaceImages;
    private String spaceDescription;
    private List<String> suitableFor;
    private int spaceCapacity;
    private List<String> locationsAvailable;
    private List<Combo> spaceCombos;
    private Status status;
}
