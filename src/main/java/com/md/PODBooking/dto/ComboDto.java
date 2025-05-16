package com.md.PODBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ComboDto {
    private String name;
    private double price;
    private String description;
}
