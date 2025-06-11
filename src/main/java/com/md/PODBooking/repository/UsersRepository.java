package com.md.PODBooking.repository;

import com.md.PODBooking.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {

    Optional<User> findByUserEmail(String email);
    Optional<User> findByUserPhone(String phone);

}
