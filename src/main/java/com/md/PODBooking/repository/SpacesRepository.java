package com.md.PODBooking.repository;


import com.md.PODBooking.entity.Space;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpacesRepository extends MongoRepository<Space, String> {
    Optional<Space> findBySpaceName(String name);
}
