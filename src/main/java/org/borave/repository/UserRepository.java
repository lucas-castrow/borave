package org.borave.repository;

import org.borave.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsById(String id);

    User findById(String id);

}
