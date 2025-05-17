package com.md.PODBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "Combo",
        description = "Schema to hold Combo information"
)
@Data
@AllArgsConstructor @NoArgsConstructor
public class ComboDto {
    @Schema(
            description = "Name of Combo", example = "Little Recharge"
    )
    @NotEmpty(message = "Combo name can not be null or empty")
    private String name;

    @Schema(
            description = "Price of Combo", example = "80000"
    )
    @NotEmpty(message = "Combo price can not be null or empty")
    private double price;

    @Schema(
            description = "Details of Combo benefits", example = "Get 1 hour bed usage. 1 drink from the menu."
    )
    @NotEmpty(message = "Combo description can not be null or empty")
    private String description;
}
