package com.md.PODBooking.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "pods")
@Data
@AllArgsConstructor @NoArgsConstructor
public class PodEntity {
    @Id
    private String id;
    private String podName;
    private List<String> podImages;
    private String podDescription;
    private int podCapacity;
    private String locationId;
    private Status status;
}
