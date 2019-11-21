package com.carrental.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.carrental.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUserName(String username);
}