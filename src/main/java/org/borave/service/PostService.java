package org.borave.service;

import org.borave.exception.PostException;
import org.borave.exception.ProfileException;
import org.borave.model.Post;
import org.borave.model.PostDTO;
import org.borave.repository.PostRepository;
import org.borave.repository.ProfileRepository;
import org.borave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public PostService(PostRepository postRepository, ProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
    }


    public Post sendPost(String postedBy, String content, List<String> authorizedUsers){
        if(profileRepository.existsById(postedBy)){
            throw new ProfileException.ProfileNotFoundException("User not found");
        }
        if(authorizedUsers.isEmpty()){
            throw new PostException.PostNotCreated("Problem to create this post.");
        }
        Post post = new Post(postedBy, content, authorizedUsers);
        return postRepository.save(post);
    }

    public List<PostDTO> findPostsSent(String userId){
        List<Post> posts = postRepository.findByAuthorizedUsersContains(userId);
        List<PostDTO> postsDTO = new ArrayList<PostDTO>() ;
        for(Post post : posts){
            postsDTO.add(post.toDTO());
        }
        return postsDTO;
    }
}
