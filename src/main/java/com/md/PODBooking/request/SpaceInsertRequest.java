package com.md.PODBooking.request;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Space",
        description = "Schema to hold Space information"
)
public record SpaceInsertRequest(
   String name,
   String description,
   String amenities,
   String suitable,

   @Schema(
           description = "Combos for Space", example = "{\"comboName\": \"Little Recharge\"," +
           "\"comboPrice\": \"80000\", \"comboDescription\": \"1 hour bed usage. 1 drink from the menu.\"}"

   )
   String combos
) {}
