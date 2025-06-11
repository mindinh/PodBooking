package com.md.PODBooking.service;


import com.md.PODBooking.dto.ComboDto;
import com.md.PODBooking.entity.Space;
import com.md.PODBooking.request.SpaceInsertRequest;
import com.md.PODBooking.request.SpaceUpdateRequest;

import java.util.List;

public interface SpaceService {
    List<Space> findAllSpaces();
    Space findSpaceByName(String name);
    void insertSpace(SpaceInsertRequest insertRequest);
    boolean addSpaceLocations(SpaceUpdateRequest updateRequest);
    boolean addCombo(String spaceName, ComboDto comboDto);
    boolean deleteCombo(String spaceName, String comboName);
}
