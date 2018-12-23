package com.vitrenko.resourceoptimizer.repository;

import com.vitrenko.resourceoptimizer.domain.AppUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<AppUser, ObjectId> {

    AppUser findByEmail(String email);
    void deleteByEmail(String email);
}
