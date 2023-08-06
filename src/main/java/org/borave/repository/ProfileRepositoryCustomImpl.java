package org.borave.repository;

import org.borave.model.Profile;
import org.borave.nats.NatsService;
import org.borave.nats.NatsSubjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ProfileRepositoryCustomImpl implements ProfileRepositoryCustom{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private NatsService natsService;

    @Override
    public Profile save(Profile profile) {
        Profile data = mongoTemplate.save(profile);

        natsService.publish(NatsSubjects.SUBJECT_PROFILE + "." + profile.getId(), data);

        return data;
    }
}
