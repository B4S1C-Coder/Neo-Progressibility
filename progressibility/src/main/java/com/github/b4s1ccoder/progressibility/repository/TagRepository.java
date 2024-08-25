package com.github.b4s1ccoder.progressibility.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.b4s1ccoder.progressibility.entity.Tag;

public interface TagRepository extends MongoRepository<Tag, String> {

}
