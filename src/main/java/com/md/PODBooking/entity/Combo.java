package com.md.PODBooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Combo {
    private String comboName;
    private double comboPrice;
    private String comboDescription;
    private int duration;
    private int extraDuration;
}
