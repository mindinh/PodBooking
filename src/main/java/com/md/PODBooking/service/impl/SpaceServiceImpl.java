package com.md.PODBooking.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.PODBooking.dto.ComboDto;
import com.md.PODBooking.dto.LocationDto;
import com.md.PODBooking.entity.Combo;
import com.md.PODBooking.entity.Location;
import com.md.PODBooking.entity.Space;
import com.md.PODBooking.entity.Status;
import com.md.PODBooking.exception.InsertException;
import com.md.PODBooking.exception.ResourceNotFoundException;
import com.md.PODBooking.mapper.ComboMapper;
import com.md.PODBooking.repository.LocationsRepository;
import com.md.PODBooking.repository.SpacesRepository;
import com.md.PODBooking.request.SpaceInsertRequest;
import com.md.PODBooking.request.SpaceUpdateRequest;
import com.md.PODBooking.service.SpaceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class SpaceServiceImpl implements SpaceService {
    private final SpacesRepository spacesRepository;
    private final LocationsRepository locationsRepository;
    private final ObjectMapper objectMapper;

    public SpaceServiceImpl(SpacesRepository spacesRepository, ObjectMapper objectMapper, LocationsRepository locationsRepository) {
        this.spacesRepository = spacesRepository;
        this.locationsRepository = locationsRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Space> findAllSpaces() {
        List<Space> spaces = spacesRepository.findAll();
        return spaces;
    }

    @Override
    public Space findSpaceByName(String name) {
        Space space = spacesRepository.findBySpaceName(name).orElseThrow(
                () -> new ResourceNotFoundException("Space", "name", name)
        );
        return space;
    }

    @Override
    public void insertSpace(SpaceInsertRequest request) {
        Space space = new Space();
        try {
            List<String> amenitiesList = objectMapper.readValue(request.amenities(), new TypeReference<>(){});
            List<String> suitableList = objectMapper.readValue(request.suitable(), new TypeReference<>(){});
            List<Combo> comboList = objectMapper.readValue(request.combos(), new TypeReference<>(){});

            space.setSpaceName(request.name());
            space.setSpaceDescription(request.description());
            space.setAmenities(amenitiesList);
            space.setSuitableFor(suitableList);
            space.setLocationsAvailable(new ArrayList<>());
            space.setSpaceCombos(comboList);
            space.setStatus(Status.ACTIVE);

            spacesRepository.save(space);
        } catch (Exception e) {
            throw new InsertException("Space insertion failed");
        }
    }

    @Override
    public boolean addSpaceLocations(SpaceUpdateRequest request) {
        Space space = spacesRepository.findBySpaceName(request.name()).orElseThrow(
                () -> new ResourceNotFoundException("Space", "Space Name", request.name())
        );
        try {
            if (request.locationName() != null) {
                Location location = locationsRepository.findLocationByLocationName(request.locationName()).orElseThrow(
                        () -> new ResourceNotFoundException("Location", "Location Name", request.locationName())
                );

                if (space.getLocationsAvailable().contains(location.getLocationName())) {
                    return false;
                }
                space.getLocationsAvailable().add(location.getLocationName());
                spacesRepository.save(space);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return true;
    }

    @Override
    public boolean addCombo(String spaceName, ComboDto comboDto) {
        Space space = spacesRepository.findBySpaceName(spaceName).orElseThrow(
                () -> new ResourceNotFoundException("Space", "Space Name", spaceName)
        );
        Combo newCombo = ComboMapper.mapToCombo(comboDto);
        if (space.getSpaceCombos().contains(newCombo)) {
            return false;
        }
        space.getSpaceCombos().add(newCombo);
        spacesRepository.save(space);
        return true;
    }

    @Override
    public boolean deleteCombo(String spaceName, String comboName) {
        Space space = spacesRepository.findBySpaceName(spaceName).orElseThrow(
                () -> new ResourceNotFoundException("Space", "Space Name", spaceName)
        );

        boolean isSuccess = space.getSpaceCombos().removeIf(combo -> Objects.equals(combo.getComboName(), comboName));

        spacesRepository.save(space);
        return isSuccess;
    }
}
