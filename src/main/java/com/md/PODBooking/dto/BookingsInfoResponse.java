package com.md.PODBooking.dto;


import com.md.PODBooking.entity.Combo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor @NoArgsConstructor
public class BookingsInfoResponse {
    private List<Map<String, List<Map<String, List<Combo>>>>> bookingsInfos;
    private Set<Map<String, List<Combo>>> spaceAndCombos;

}
