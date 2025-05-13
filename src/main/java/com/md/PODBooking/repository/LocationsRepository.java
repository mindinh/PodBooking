package com.md.PODBooking.repository;

import com.md.PODBooking.entity.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationsRepository extends MongoRepository<Location, String> {
    Optional<Location> findLocationByLocationName(String locationName);
}
