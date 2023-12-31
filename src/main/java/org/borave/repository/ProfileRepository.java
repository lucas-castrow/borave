package org.borave.repository;

import org.borave.model.Profile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, ObjectId>, ProfileRepositoryCustom {
    Profile findById(String id);

    boolean existsById(String id);

    Profile findByUserId(String userId);

    public <S extends Profile> S save(S profile);
}
