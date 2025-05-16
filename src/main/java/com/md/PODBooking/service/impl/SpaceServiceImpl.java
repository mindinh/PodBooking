package com.md.PODBooking.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.PODBooking.dto.ComboDto;
import com.md.PODBooking.entity.Combo;
import com.md.PODBooking.entity.Space;
import com.md.PODBooking.entity.Status;
import com.md.PODBooking.exception.InsertException;
import com.md.PODBooking.exception.ResourceNotFoundException;
import com.md.PODBooking.mapper.ComboMapper;
import com.md.PODBooking.repository.SpacesRepository;
import com.md.PODBooking.request.SpaceInsertRequest;
import com.md.PODBooking.request.SpaceUpdateRequest;
import com.md.PODBooking.service.SpaceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SpaceServiceImpl implements SpaceService {
    private SpacesRepository spacesRepository;
    private ObjectMapper objectMapper;
    public SpaceServiceImpl(SpacesRepository spacesRepository, ObjectMapper objectMapper) {
        this.spacesRepository = spacesRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Space> findAllSpaces() {
        List<Space> spaces = spacesRepository.findAll();
        return spaces;
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
            space.setSpaceCombos(comboList);
            space.setStatus(Status.ACTIVE);

            spacesRepository.save(space);
        } catch (Exception e) {
            throw new InsertException("Space insertion failed");
        }
    }

    @Override
    public boolean updateSpace(SpaceUpdateRequest request) {
        Space space = spacesRepository.findBySpaceName(request.name()).orElseThrow(
                () -> new ResourceNotFoundException("Space", "Space Name", request.name())
        );
        try {
            List<Integer> locationIdList = new ArrayList<>();
            if (request.locations() != null) {
                locationIdList = objectMapper.readValue(request.locations(), new TypeReference<>(){});
            }


            space.setLocationId(locationIdList);
            spacesRepository.save(space);
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

        return false;
    }
}
