package org.borave.repository;

import org.borave.model.Post;
import org.borave.model.Profile;
import org.borave.nats.NatsService;
import org.borave.nats.NatsSubjects;
import org.borave.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class PostRepositoryCustomImpl implements PostRepositoryCustom{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private NatsService natsService;

    @Autowired
    private ProfileService profileService;

    @Override
    public Post save(Post post) {
        Post data = mongoTemplate.save(post);
        return data;
    }

    @Override
    public List<Post> findPostsByAuthorizedUsers(String profileId) {
        List<Post> posts = mongoTemplate.findAll(Post.class);

        List<Post> authorizedPosts = posts.stream()
                .filter(post -> post.getAuthorizedUsers().contains(profileId))
                .collect(Collectors.toList());

        return authorizedPosts;
    }
}
