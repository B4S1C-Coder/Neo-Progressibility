package com.github.b4s1ccoder.progressibility.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.b4s1ccoder.progressibility.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);
    User findByEmail(String email);
}
