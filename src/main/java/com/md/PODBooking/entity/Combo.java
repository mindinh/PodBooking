package com.md.PODBooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class Combo {
    private String comboName;
    private double comboPrice;
    private String comboDescription;
}
