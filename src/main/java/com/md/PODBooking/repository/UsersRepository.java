package com.md.PODBooking.repository;

import com.md.PODBooking.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByUserEmail(String email);

}
