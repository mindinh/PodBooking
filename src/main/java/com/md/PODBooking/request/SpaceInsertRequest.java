package com.md.PODBooking.request;

public record SpaceInsertRequest(
   String name,
   String description,
   String amenities,
   String suitable,
   String combos
) {}
