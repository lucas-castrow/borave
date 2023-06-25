package org.borave.service;

import org.borave.exception.PostException;
import org.borave.exception.UserException;
import org.borave.model.Post;
import org.borave.model.User;
import org.borave.repository.PostRepository;
import org.borave.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    public Post createPost(String postedBy, String content, List<String> authorizedUsernames){
        List<String> authorizedUsers = new ArrayList<>();
        for (String username : authorizedUsernames) {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                authorizedUsers.add(user.getId());
            }
        }
        if(authorizedUsers.isEmpty()){
            throw new PostException.PostNotCreated("Problem to create this post.");
        }
        Post post = new Post(postedBy, content, authorizedUsers);
        return postRepository.save(post);
    }
}
