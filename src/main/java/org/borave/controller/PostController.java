package org.borave.controller;

import org.borave.config.JwtTokenProvider;
import org.borave.exception.ProfileException;
import org.borave.model.ApiResponse;
import org.borave.model.Post;
import org.borave.model.PostDTO;
import org.borave.model.ProfileDTO;
import org.borave.service.PostService;
import org.borave.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final ProfileService profileService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public PostController(PostService postService, ProfileService profileService, JwtTokenProvider jwtTokenProvider) {
        this.postService = postService;
        this.profileService = profileService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/sendPost")
    public ResponseEntity<ApiResponse> sendPost(@RequestBody Map<String, Object> request, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        ApiResponse response;
        try {
            String postedBy = (String) request.get("postedBy");
            String content = (String) request.get("content");
            List<String> authorizedUsers = (List<String>) request.get("authorizedUsers");

            if (!jwtTokenProvider.isRealUser(token, profileService.getUsernameByUserId(postedBy))) {
                response = new ApiResponse<>(false, "Bearer token invalid for this user.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            Post createdPost = postService.sendPost(postedBy, content, authorizedUsers);
            response = new ApiResponse(true, "Post created successfully", createdPost);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response = new ApiResponse(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> posts(@PathVariable ("id") String id,  @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        ApiResponse response;
        try {
            if (!jwtTokenProvider.isRealUser(token, profileService.getUsernameByUserId(id))) {
                response = new ApiResponse<>(false, "Bearer token invalid for this user.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            List<PostDTO> postsDTO = postService.findPostsSent(id);
            response = new ApiResponse(true, "Posts loaded", postsDTO);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response = new ApiResponse(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
