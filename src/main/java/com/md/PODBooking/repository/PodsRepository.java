package com.md.PODBooking.repository;


import com.md.PODBooking.entity.PodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PodsRepository extends MongoRepository<PodEntity, String> {
}
