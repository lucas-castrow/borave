package org.borave.service;

import org.borave.exception.PostException;
import org.borave.exception.ProfileException;
import org.borave.model.Post;
import org.borave.model.PostDTO;
import org.borave.model.ProfileDTO;
import org.borave.repository.PostRepository;
import org.borave.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    private final ProfileService profileService;

    @Autowired
    public PostService(PostRepository postRepository, ProfileRepository profileRepository, ProfileService profileService) {
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
        this.profileService = profileService;
    }


    public Post sendPost(String postedBy, String content, List<String> authorizedUsers){
        if(!profileRepository.existsById(postedBy)){
            throw new ProfileException.ProfileNotFoundException("User not found");
        }
        if(authorizedUsers.isEmpty()){
            throw new PostException.PostNotCreated("Problem to create this post.");
        }
        Post post = new Post(postedBy, content, authorizedUsers);
        return postRepository.save(post);
    }

    public List<PostDTO> findPostsSent(String userId){
        List<Post> posts = postRepository.findPostsByAuthorizedUsers(userId);
        List<PostDTO> postsDTO = new ArrayList<PostDTO>();
        for(Post post : posts){
            Optional<PostDTO> result = postsDTO.stream()
                    .filter(obj -> obj.getPostedBy().equals(post.getPostedBy()))
                    .findFirst();
            if(!result.isPresent()) {
                ProfileDTO senderProfile = profileService.getProfile(post.getPostedBy());
                PostDTO postDTO = post.toDTO(senderProfile.getUsername());
                postDTO.setSenderPhoto(senderProfile.getPhoto());
                postDTO.setFriendLevelStories(profileService.getMyProfile(senderProfile.getUsername()).getFriendLevelStories(senderProfile.getId()));
                postsDTO.add(postDTO);
            }else{
                PostDTO postDTO = result.get();
                postDTO.addContent(post.getContent());
            }
        }
        return postsDTO;
    }
}
