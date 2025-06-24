package com.md.PODBooking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.PODBooking.dto.BookingsInfoResponse;
import com.md.PODBooking.entity.Combo;
import com.md.PODBooking.entity.Location;
import com.md.PODBooking.entity.Space;
import com.md.PODBooking.repository.LocationsRepository;
import com.md.PODBooking.repository.SpacesRepository;
import com.md.PODBooking.request.BookingRequest;
import com.md.PODBooking.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingsServiceImpl implements BookingService {

    private final LocationsRepository locationsRepository;
    private final SpacesRepository spacesRepository;
    private final ObjectMapper objectMapper;

    public BookingsServiceImpl(LocationsRepository locationsRepository, SpacesRepository spacesRepository, ObjectMapper objectMapper) {
        this.locationsRepository = locationsRepository;
        this.spacesRepository = spacesRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public BookingsInfoResponse showBookingsInfo() {
        BookingsInfoResponse response = new BookingsInfoResponse();

        List<Map<String, List<Map<String, List<Combo>>>>> bookingsInfo = new ArrayList<>();
        Set<Map<String, List<Combo>>> spaceComboMap = new HashSet<>();

        List<Location> locations = locationsRepository.findAll();

        for (Location location : locations) {
            Map<String, List<Map<String, List<Combo>>>> locationMap = new HashMap<>();



            List<Map<String, List<Combo>>> spaces = new ArrayList<>();
            for (String s : location.getAvailableSpaces().keySet()) {
                Map<String, List<Combo>> spaceMap = new HashMap<>();

                Optional<Space> space = spacesRepository.findBySpaceName(s);

                if (space.isPresent()) {
                    List<Combo> combos = space.get().getSpaceCombos();
                    spaceMap.put(space.get().getSpaceName(), combos);
                }
                spaces.add(spaceMap);
                spaceComboMap.add(spaceMap);
            }
            locationMap.put(location.getLocationName(), spaces);
            bookingsInfo.add(locationMap);

        }

        response.setBookingsInfos(bookingsInfo);
        response.setSpaceAndCombos(spaceComboMap);

        return response;
    }

    @Override
    public void placeBooking(BookingRequest request) {

    }
}
